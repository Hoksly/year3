import sys
import struct
import math
import argparse

# Constants
S = [
    7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,
    5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,
    4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,
    6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21,
]

K = [
    0xd76aa478, 0xe8c7b756, 0x242070db, 0xc1bdceee,
    0xf57c0faf, 0x4787c62a, 0xa8304613, 0xfd469501,
    0x698098d8, 0x8b44f7af, 0xffff5bb1, 0x895cd7be,
    0x6b901122, 0xfd987193, 0xa679438e, 0x49b40821,
    0xf61e2562, 0xc040b340, 0x265e5a51, 0xe9b6c7aa,
    0xd62f105d, 0x02441453, 0xd8a1e681, 0xe7d3fbc8,
    0x21e1cde6, 0xc33707d6, 0xf4d50d87, 0x455a14ed,
    0xa9e3e905, 0xfcefa3f8, 0x676f02d9, 0x8d2a4c8a,
    0xfffa3942, 0x8771f681, 0x6d9d6122, 0xfde5380c,
    0xa4beea44, 0x4bdecfa9, 0xf6bb4b60, 0xbebfbc70,
    0x289b7ec6, 0xeaa127fa, 0xd4ef3085, 0x04881d05,
    0xd9d4d039, 0xe6db99e5, 0x1fa27cf8, 0xc4ac5665,
    0xf4292244, 0x432aff97, 0xab9423a7, 0xfc93a039,
    0x655b59c3, 0x8f0ccc92, 0xffeff47d, 0x85845dd1,
    0x6fa87e4f, 0xfe2ce6e0, 0xa3014314, 0x4e0811a1,
    0xf7537e82, 0xbd3af235, 0x2ad7d2bb, 0xeb86d391,
]

# Left rotate function
def left_rotate(x, c):
    return ((x << c) | (x >> (32 - c))) & 0xffffffff

# Pre-processing functions
def pad_message(message):
    original_byte_len = len(message)
    original_bit_len = original_byte_len * 8

    # Append the bit '1' to the message
    message += b'\x80'

    # Append 0 <= k < 512 bits '0', such that the resulting message length in bits
    # is congruent to 448 (mod 512)
    message += b'\x00' * ((56 - (original_byte_len + 1) % 64) % 64)

    # Append length of message (before pre-processing), in bits, as 64-bit little-endian integer
    message += struct.pack('<Q', original_bit_len)

    return message

# Process the message in 512-bit chunks
def process_chunk(chunk, h):
    # Break chunk into sixteen 32-bit words M[j], 0 ≤ j ≤ 15
    M = struct.unpack('<16I', chunk)

    # Initialize hash value for this chunk
    A, B, C, D = h

    # Main loop
    for i in range(64):
        if 0 <= i <= 15:
            F = (B & C) | (~B & D)
            g = i
        elif 16 <= i <= 31:
            F = (D & B) | (~D & C)
            g = (5*i + 1) % 16
        elif 32 <= i <= 47:
            F = B ^ C ^ D
            g = (3*i + 5) % 16
        elif 48 <= i <= 63:
            F = C ^ (B | ~D)
            g = (7*i) % 16

        F = (F + A + K[i] + M[g]) & 0xffffffff
        A = D
        D = C
        C = B
        B = (B + left_rotate(F, S[i])) & 0xffffffff

    # Update hash value
    h[0] = (h[0] + A) & 0xffffffff
    h[1] = (h[1] + B) & 0xffffffff
    h[2] = (h[2] + C) & 0xffffffff
    h[3] = (h[3] + D) & 0xffffffff

# Compute MD5 hash
def compute_md5_hash(data):
    # Initialize variables
    h = [0x67452301, 0xefcdab89, 0x98badcfe, 0x10325476]

    # Process the data in successive 512-bit chunks
    for i in range(0, len(data), 64):
        process_chunk(data[i:i+64], h)

    # Format the digest
    return struct.pack('<4I', *h).hex()

def md5_hash_from_string(input_string):
    return compute_md5_hash(input_string.encode('utf-8'))

def md5_hash_from_file(file_name):
    try:
        with open(file_name, 'rb') as f:
            data = f.read()
            return compute_md5_hash(data)
    except FileNotFoundError:
        print(f"Error: File '{file_name}' not found.")
        sys.exit(1)

def main():
    parser = argparse.ArgumentParser(description='Compute MD5 hash of a string or file.')
    parser.add_argument('-s', '--string', type=str, help='Input string to hash')
    parser.add_argument('-f', '--file', type=str, help='Input file to hash')
    
    if len(sys.argv)==1:
        parser.print_help(sys.stderr)
        sys.exit(1)

    args = parser.parse_args()

    if args.string:
        print(f"MD5 hash of '{args.string}': {md5_hash_from_string(args.string)}")
    elif args.file:
        print(f"MD5 hash of file '{args.file}': {md5_hash_from_file(args.file)}")

if __name__ == "__main__":
    main()
