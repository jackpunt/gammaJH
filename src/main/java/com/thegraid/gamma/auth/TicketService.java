package com.thegraid.gamma.auth;

import com.thegraid.gamma.domain.User;
import com.thegraid.gamma.service.UserService;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class TicketService {

    protected static final Logger log = LoggerFactory.getLogger(TicketService.class);

    protected UserService userService;

    TicketService(UserService userService) {
        this.userService = userService;
    }

    /**
     * create a time-limited, validate-able String, with labeled properties.
     * [implicit arg: username]
     * @parma user
     * @param salt either the user.salt OR salt(UUID=JSESSIONID) ?
     * @param validTime
     * @param args Key Value ...
     * @return
     */
    // 2 cases: salt from JSESSIONID/UUID [EmailTicket] -or- salt from member.getSalt() [GameTicket]
    private String getGenericTicket(User user, Long salt, long validTime, String... args) {
        // compose: ^P=(1)&(T=(ddd)&U=(username)&...)$
        String username = user.getLogin();
        Long timelimit = new Date().getTime() + validTime;
        StringBuilder sb = new StringBuilder();
        sb.append("&T=").append(Long.toHexString(timelimit));
        sb.append("&U=").append(username);
        for (int ndx = 0; ndx < args.length; ndx += 2) {
            sb.append("&").append(args[ndx]).append("=").append(args[ndx + 1]);
        }
        String raw = sb.toString();
        String token = signature(raw, salt); // encode ticket with theSalt
        return "P=" + token + raw;
    }

    /** get Signature/hash for given text + salt. */
    private String signature(String text, Long salt) {
        String algo = "SHA-256"; // or SHA-512?
        try {
            MessageDigest digest = MessageDigest.getInstance(algo);
            byte[] text_bytes = text.getBytes(StandardCharsets.UTF_8);
            byte[] salt_bytes = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN).putLong(salt).array();
            byte[] hash = digest.digest(concatBytes(salt_bytes, text_bytes));
            String rv = Base64.getEncoder().encodeToString(hash);
            return rv;
        } catch (NoSuchAlgorithmException ex) {
            log.error("Error: {}", ex);
            return ""; // only if algo not found
        }
    }

    private byte[] concatBytes(byte[] bytes1, byte[] bytes2) {
        int len = bytes1.length + bytes2.length;
        byte[] all_bytes = Arrays.copyOf(bytes1, len);
        System.arraycopy(bytes2, 0, all_bytes, len, bytes2.length);
        return all_bytes;
    }

    public static class Ticket {

        public boolean match = false;
        public String token; // @RequestParam("P")
        public String raw2; // (&T=...&U=...)
        public String tls; // @RequestParam("T")
        public String user; // @RequestParam("U")
        public String rest; // @RequestParam("V") or whatever

        protected Ticket(Pattern pat, CharSequence raw) {
            if (raw == null) return;
            Matcher matcher = pat.matcher(raw);
            match = matcher.matches();
            if (!match) return;
            token = matcher.group("P"); // P=encode(raw2,theSalt)
            raw2 = matcher.group("raw2"); // raw2=(&T=...&U=...)
            tls = matcher.group("T"); // T=expiryTime
            user = matcher.group("U"); // U=loginId [logged and validated in hash]
            rest = matcher.group("rest"); // possibly null or empty
        }
    }

    Boolean validateTicket(Pattern pat, CharSequence raw, Long salt) {
        log.trace("pat={}", pat);
        log.trace("raw={}", raw);
        Ticket ticket = new Ticket(pat, raw);
        if (!ticket.match) {
            log.debug("not a ticket: {}", raw.length());
            return null;
        }

        String hash = signature(ticket.raw2, salt);
        if (!hash.equals(ticket.token)) {
            log.trace(String.format("hash=%s,token=%s,raw2=%s\n", hash, ticket.token, ticket.raw2));
            log.debug("TicketService.validateTicket={}, user={}", "(corrupt)", ticket.user);
            return false; // corrupt data: throw new BadCredentialsException("") ?
        }
        long timelimit = Long.parseLong(ticket.tls, 16);
        if (new Date().getTime() > timelimit) {
            log.debug("TicketService.validateTicket={}, user={}", "(expired)", ticket.user);
            //throw new CredentialsExpiredException("User can try again with a new token");
            return false;
        }
        log.debug("TicketService.validateTicket={}, user={}", true, ticket.user);
        return true;
    }

    /** suitable for using JSESSIONID as a per-user/session Salt */
    public Long getSaltFromUUID(String uuids) {
        String uuidss =
            uuids.substring(0, 7) +
            "-" +
            uuids.substring(8, 11) +
            "-" +
            uuids.substring(12, 15) +
            "-" +
            uuids.substring(16, 19) +
            "-" +
            uuids.substring(20);
        UUID uuid = UUID.fromString(uuidss); // all this to convert string to pair of long
        long uuid_salt = (uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits());
        log.trace("getSaltFomrUUID: {} --> {}", uuids, uuid_salt);
        return (Long) uuid_salt;
    }

    private final Long no_salt = 5345909788840343976L; // or 0 or null I suppose
    private final BCryptSaltSource bcss = new BCryptSaltSource();

    public Long getSaltFromUser(User user) {
        String pw = user.getPassword();
        if (pw.length() < 60) return no_salt;
        // 0:{bcrypt}, 1:2a, 2:12, 3:salt+passwd
        String salt22 = pw.split("\\$", 4)[3].substring(0, 22);
        return bcss.decodeSalt(salt22);
    }

    static class BCryptSaltSource {

        long decodeSalt(String salt22) {
            String base64Salt = utilStringTr(salt22, CHARS_BCRYPT, CHARS_BASE64);
            byte[] bytes = Base64.getDecoder().decode(base64Salt); // 8 bytes
            return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getLong();
        }

        long byteToLong(byte[] bytes) {
            return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getLong();
        }

        byte[] longToBytes(long number) {
            return ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN).putLong(number).array();
        }

        /**
         * BCrypt base64 encoding alphabet
         */
        static String CHARS_BCRYPT = "./ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        /**
         * RFC-4648 base64 encoding alphabet
         */
        static String CHARS_BASE64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

        /** translate chars of str from xin to xout */
        String utilStringTr(String str, String xin, String xout) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                int ndx = xin.indexOf(str.substring(i, i + 1));
                sb.append(xout.substring(ndx, ndx + 1));
            }
            return sb.toString();
        }
    }

    @Service
    public static class EmailTicketService extends TicketService {

        EmailTicketService(UserService userService) {
            super(userService);
        }

        /** P=(40-digit Token)(&T=(11-xDigit timelimit)&U=(username)&...) */
        private final Pattern pat = Pattern.compile(
            "P=(?<P>\\p{XDigit}{40,})(?<raw2>&T=(?<T>\\p{XDigit}{11,})&U=(?<U>[^&]+))(?<rest>&.*)?$"
        );

        /**
         * Create an email-able hash token encapsulating login credentials.
         * Create ticket for *this* user, include additional info in token:
         * passwordUserDetails.getEmailTicket(time, "V", iview.getId(), "W",
         * job.getId())
         *
         * @param user      the Member to login (so we can getSalt/secret)
         * @param validTime deadlne for ticket to be valid
         * @param args      &"key"="value" to be included in ticket
         * @return a String that can be validated by NoFormLoginDecoder.
         */
        public String getTicket(User user, long validTime, String... args) {
            // do not modify pwh or salt if already exists
            Long genSalt = getSaltFromUser(user); // assert(genSalt != null)
            return super.getGenericTicket(user, genSalt, validTime, args);
        }

        public boolean validateTicket(CharSequence raw, Long salt) {
            return super.validateTicket(pat, raw, salt);
        }

        public Ticket parseTicket(String query) {
            return new Ticket(pat, query);
        }
    }

    @Service
    public static class GameTicketService extends TicketService {

        GameTicketService(UserService userService) {
            super(userService);
        }

        /** P=(\p{XDigit}{64})(&T=(\p{XDigit}{11,})&U=([^&]+)&V=(\d{1,19}))(&.*)?$ */
        private final Pattern pat = Pattern.compile(
            // "P=(\\p{XDigit}{40,})(&T=(\\p{XDigit}{11,})&U=([^&]+)&V=(\\d{1,19}))(&.*)?$");
            // "P=(\\p{XDigit}{64})(&T=(\\p{XDigit}{11,})&U=([^&]+)&V=(\\d{1,19}))(&.*)?$");
            "P=(?<P>\\p{XDigit}{64})(?<raw2>&T=(?<T>\\p{XDigit}{11,})&U=(?<U>[^&]+)&V=(\\d{1,19}))(?<rest>&.*)?$"
        );

        /**
         * Create query string Ticket for DisplayClient to authenticate to game server.
         *
         * @param user      include user.loginid
         * @param validTime deadlne for ticket to be valid
         * @param gpid      the long id (not null, not 0L)
         * @param uuids     String representing a UUID that is shared with the
         *                  GameServer.
         */
        public String getTicket(User user, Long validTime, Long gpid, String uuids) {
            return getTicket(user, validTime, gpid, getSaltFromUUID(uuids));
        }

        // export this if there is some other salt to share with the validating side
        private String getTicket(User user, Long validTime, Long gpid, Long salt) {
            return super.getGenericTicket(user, salt, validTime, "V", gpid.toString());
        }

        public boolean validateTicket(CharSequence raw, Long salt) {
            return super.validateTicket(pat, raw, salt);
        }

        public Ticket parseTicket(String query) {
            return new Ticket(pat, query);
        }
    }
}
