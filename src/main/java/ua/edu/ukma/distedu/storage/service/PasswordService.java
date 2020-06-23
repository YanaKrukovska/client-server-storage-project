package ua.edu.ukma.distedu.storage.service;

public interface PasswordService {

    String encodePassword(String password);
    boolean comparePasswordAndConfirmationPassword(String password, String confirmationPassword);
    boolean compareRawAndEncodedPassword(String raw, String encoded);
}
