import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;

public class PasswordManager {
    
    // Generate a strong password using a password generator library
    public static String generatePassword() {
        // You can use any password generator library of your choice
        // For example, here we generate a random alphanumeric password of length 12
        String passwordChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 12; i++) {
            int randomIndex = random.nextInt(passwordChars.length());
            password.append(passwordChars.charAt(randomIndex));
        }
        return password.toString();
    }
    
    // Encrypt the password before storing it
    public static String encryptPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Use PBKDF2 algorithm for password-based encryption
        int iterations = 10000;
        char[] chars = password.toCharArray();
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
    }
    
    // Example usage
    public static void main(String[] args) {
        try {
            // Generate a strong password
            String newPassword = generatePassword();
            System.out.println("Generated Password: " + newPassword);
            
            // Encrypt the password before storing it
            String encryptedPassword = encryptPassword(newPassword);
            System.out.println("Encrypted Password: " + encryptedPassword);
            
            // Store the encrypted password securely
            // You can store it in a database or a secure file storage
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
