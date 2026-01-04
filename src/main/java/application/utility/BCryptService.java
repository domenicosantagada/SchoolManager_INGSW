package application.utility;

import org.mindrot.jbcrypt.BCrypt;

// Utility per hash e verifica password con BCrypt
public class BCryptService {

    // Genera hash da password in chiaro
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(4));
    }

    // Verifica se la password in chiaro corrisponde all'hash
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
