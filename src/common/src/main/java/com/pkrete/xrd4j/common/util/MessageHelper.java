package com.pkrete.xrd4j.common.util;

import com.pkrete.xrd4j.common.member.ObjectType;
import com.pkrete.xrd4j.common.member.ConsumerMember;
import com.pkrete.xrd4j.common.member.ProducerMember;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class offers some helper methods for handling ServiceRequest and
 * ServiceResponse messages.
 *
 * @author Petteri Kivimäki
 */
public class MessageHelper {

    private static final Logger logger = LoggerFactory.getLogger(MessageHelper.class);

    /**
     * Constructs and initializes a new MessageHelper object. Should never be
     * used.
     */
    private MessageHelper() {
    }

    /**
     * Returns the ObjectType of the given ConsumerMember object. The object
     * type is member if subsystem code is not defined. If subsystem code is
     * defined, the object type is subsystem.
     *
     * @param consumer the ConsumerMember object which ObjectType needs to be
     * fetched.
     * @return ObjectType of the given ConsumerMember, member or subsystem
     */
    public static ObjectType getObjectType(ConsumerMember consumer) {
        if (consumer.getSubsystemCode() == null || consumer.getSubsystemCode().isEmpty()) {
            return ObjectType.MEMBER;
        }
        return ObjectType.SUBSYSTEM;
    }

    /**
     * Returns the ObjectType of the given ProducerMember object. The object
     * type is central service if member class is not defined. If member class
     * is defined, the object type is service.
     *
     * @param producer the ProducerMember object which ObjectType needs to be
     * fetched.
     * @return ObjectType of the given ProducerMember, central service or
     * service
     */
    public static ObjectType getObjectType(ProducerMember producer) {
        if (producer.getMemberClass() == null) {
            return ObjectType.CENTRALSERVICE;
        }
        return ObjectType.SERVICE;
    }

    /**
     * Generates a universally unique identifier and returns it as a string.
     *
     * @return universally unique identifier as a string
     */
    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Parses the string argument as a signed decimal integer. If parsing of the
     * string fails, zero is returned.
     *
     * @param source a String containing the int representation to be parsed
     * @return the integer value represented by the argument in decimal
     */
    public static int strToInt(String source) {
        try {
            return Integer.parseInt(source);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    /**
     * Converts the given string to boolean. The comparison is case insensitive.
     *
     * @param source boolean value as a string
     * @return boolean value
     */
    public static boolean strToBool(String source) {
        if (source == null || source.isEmpty()) {
            return false;
        }
        return "true".equalsIgnoreCase(source);
    }

    /**
     * Calculates Base64 encoded hash of the given data string. SHA-512
     * algorithm is used for calculating the hash.
     *
     * @param data string to be hashed
     * @return Base64 encoded hash of the given data string
     */
    public static String calculateHash(String data) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-512");

            // must specify "UTF-8" encoding
            sha.update(data.getBytes("UTF-8"));
            byte[] hashedByteArray = sha.digest();

            // Use Base64 encoding here -->
            return DatatypeConverter.printBase64Binary(hashedByteArray);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * Decodes the given base64 coded image string to image.
     *
     * @param imgStr base64 coded image string to decode
     * @return decoded image
     */
    public static BufferedImage decodeStr2Image(String imgStr) {

        BufferedImage image = null;
        byte[] imageByte;
        try {
            imageByte = DatatypeConverter.parseBase64Binary(imgStr);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return image;
    }

    /**
     * Encodes the given image to a base64 coded string.
     *
     * @param image image to encode
     * @param type type of the image: jpeg, bmp, png, gif etc.
     * @return encoded string
     */
    public static String encodeImg2String(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            imageString = DatatypeConverter.printBase64Binary(imageBytes);
            bos.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return imageString;
    }

    /**
     * Encodes the given image InputStream to a base64 coded string.
     *
     * @param is image InputStream to encode
     * @param type type of the image: jpeg, bmp, png, gif etc.
     * @return encoded string
     */
    public static String encodeImg2String(InputStream is, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            BufferedImage image = ImageIO.read(is);
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            imageString = DatatypeConverter.printBase64Binary(imageBytes);
            bos.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return imageString;
    }
}
