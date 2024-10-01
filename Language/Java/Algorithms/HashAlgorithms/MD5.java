package Language.Java.Algorithms.HashAlgorithms;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;


public class MD5 {

    // Initializing hard coded values to be used
    private static final int[] INITIAL_VALUES = {
        0x67452301, // A
        0xEFCDAB89, // B
        0x98BADCFE, // C
        0x10325476  // D
    };
    private static final int[] K = new int[64];
    static{
        for (int i = 0; i < K.length; i++) {
            K[i] = (int) (long) ((1L << 32) * Math.abs(Math.sin(i + 1)));
            
        }
    }
    private static final int[] SHIFT_AMOUNTS = {
        7, 12, 17, 22, 5,  9, 14, 20,
        4, 11, 16, 23, 6, 10, 15, 21
    };

    // MD5 function

    public static String getMD5(String input){
        byte[] message = input.getBytes();
        byte[] paddedMessage = padMessage(message);
        int [] buffer = Arrays.copyOf(INITIAL_VALUES, 4);
        for (int i = 0; i < paddedMessage.length / 64; i++) {
            int [] M = new int[16]; // divide 64 bytes (512 bits) into 16 parts with 4 bytes each (32 bits)
            ByteBuffer.wrap(paddedMessage,i * 64, 64).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer().get(M);
            processChunk(M, buffer);
        }
        ByteBuffer resultBuffer = ByteBuffer.allocate(16).order(ByteOrder.LITTLE_ENDIAN);
        // Add new buffer states
        resultBuffer.putInt(buffer[0]);
        resultBuffer.putInt(buffer[1]);
        resultBuffer.putInt(buffer[2]);
        resultBuffer.putInt(buffer[3]);

        byte[] byteArray = resultBuffer.array();
        StringBuilder hexCode = new StringBuilder();

        for(byte b : byteArray){
            String hex = Integer.toHexString(0xff & b);
            if(hex.length() == 1) hexCode.append('0');
            hexCode.append(hex);
            
        }
        return hexCode.toString();



    }

    private static byte[] padMessage(byte[] message){
        int length = message.length;
        int paddingLength = (56 - (length + 1) % 64 + 64) % 64;
        ByteBuffer byteBuffer = ByteBuffer.allocate(length + 1 + paddingLength + 8).order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(message);
        byteBuffer.put((byte) 0x80); // Insert 1 bit
        byteBuffer.position(byteBuffer.capacity() - 8); // Position the cursor to the last 8 bytes to insert the original length in 64 bits (8 bytes)
        byteBuffer.putLong((long) length * 8); // Put the original length of message in 64 bits
        return byteBuffer.array();
    }

    private static void processChunk(int[] M, int[] buffer){
        int A =  buffer[0], B = buffer[1], C = buffer[2], D = buffer[3]; //Initial state of the hash
        int F,g;
        for (int i = 0; i < 64; i++) {
            if(i < 16){
                F = (B & C) | (~B & D);
                g = i;
            } else if(i < 32){
                F = (D & B) | (~D & C);
                g = (5 * i + 1) % 16;
            } else if(i < 48){
                F = B ^ C ^ D;
                g = (3 * i + 5) % 16;
            } else {
                F = C ^ (B | ~D);
                g = (7*i)%16;
            }
            //Update its values after each bitwise operations
            F = F + A + M[g] + K[i];
            A = D;
            D = C;
            C = B;
            B = B + Integer.rotateLeft(F, SHIFT_AMOUNTS[(i / 16) * 4 + i % 4]);
        }
        // Update the new vals into buffer. 
        buffer[0] += A;
        buffer[1] += B;
        buffer[2] += C;
        buffer[3] += D;
    }

    public static void main(String[] args) {
        String myName = "Ryan";
        String hashCode = getMD5(myName);
        System.out.println(hashCode); // Expected Output: 131b98dac8609f781484f08c22a8abaa
    }
}
