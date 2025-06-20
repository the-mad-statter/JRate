package com.themadstatter.pathfinder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader; 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EasyReader {
     protected String myFileName;
     protected BufferedReader myInFile;
     protected int myErrorFlags = 0;
     protected static final int OPENERROR = 0x0001;
     protected static final int CLOSEERROR = 0x0002;
     protected static final int READERROR = 0x0004;
     protected static final int EOF = 0x0100;

   /**
    *  Constructor.  Prepares console (System.in) for reading
    */
   public EasyReader()
   {
     myFileName = null;
     myErrorFlags = 0;
     myInFile = new BufferedReader(
                             new InputStreamReader(System.in), 128);
   }

   /**
    *  Constructor.  opens a file for reading
    *  @param fileName the name or pathname of the file
    */
   public EasyReader(String fileName)
   {
     myFileName = fileName;
     myErrorFlags = 0;
     try
     {
       myInFile = new BufferedReader(new FileReader(fileName), 1024);
     }
     catch (FileNotFoundException e)
     {
       myErrorFlags |= OPENERROR;
       myFileName = null;
     }
   }
   
   /**
    *  Constructor.  Prepares an InputStream for reading
    *  @param in the InputStream from which to read
    */
   public EasyReader(InputStream in) {
     myFileName = null;
     myErrorFlags = 0;
     myInFile = new BufferedReader(new InputStreamReader(in), 128);
   }

   /**
    *  Closes the file
    */
   public void close()
   {
     if (myFileName == null)
       return;
     try
     {
       myInFile.close();
     }
     catch (IOException e)
     {
       System.err.println("Error closing " + myFileName + "\n");
       myErrorFlags |= CLOSEERROR;
     }
   }

   /**
    *  Checks the status of the file
    *  @return true if en error occurred opening or reading the file,
    *  false otherwise
    */
   public boolean bad()
   {
     return myErrorFlags != 0;
   }

   /**
    *  Checks the EOF status of the file
    *  @return true if EOF was encountered in the previous read
    *  operation, false otherwise
    */
   public boolean eof()
   {
     return (myErrorFlags & EOF) != 0;
   }

   private boolean ready() throws IOException
   {
     return myFileName == null || myInFile.ready();
   }

   /**
    *  Reads the next character from a file (any character including
    *  a space or a newline character).
    *  @return character read or <code>null</code> character
    *  (Unicode 0) if trying to read beyond the EOF
    */
   public char readChar()
   {
     char ch = '\u0000';

     try
     {
       if (ready())
       {
          ch = (char)myInFile.read();
       }
     }
     catch (IOException e)
     {
       if (myFileName != null)
         System.err.println("Error reading " + myFileName + "\n");
       myErrorFlags |= READERROR;
     }

     if (ch == '\u0000')
       myErrorFlags |= EOF;

     return ch;
   }

   /**
    *  Reads from the current position in the file up to and including
    *  the next newline character.  The newline character is thrown away
    *  @return the read string (excluding the newline character) or
    *  null if trying to read beyond the EOF
    */
   public String readLine()
   {
     String s = null;

     try
     {
       s = myInFile.readLine();
     }
     catch (IOException e)
     {
       if (myFileName != null)
         System.err.println("Error reading " + myFileName + "\n");
       myErrorFlags |= READERROR;
     }

     if (s == null)
       myErrorFlags |= EOF;
     return s;
   }

   /**
    *  Skips whitespace and reads the next word (a string of consecutive
    *  non-whitespace characters (up to but excluding the next space,
    *  newline, etc.)
    *  @return the read string or null if trying to read beyond the EOF
    */
   public String readWord()
   {
     StringBuffer buffer = new StringBuffer(128);
     char ch = ' ';
     int count = 0;
     String s = null;

     try
     {
       while (ready() && Character.isWhitespace(ch))
         ch = (char)myInFile.read();
       while (ready() && !Character.isWhitespace(ch))
       {
         count++;
         buffer.append(ch);
         myInFile.mark(1);
         ch = (char)myInFile.read();
       };

       if (count > 0)
       {
         myInFile.reset();
         s = buffer.toString();
       }
       else
       {
         myErrorFlags |= EOF;
       }
     }

     catch (IOException e)
     {
       if (myFileName != null)
         System.err.println("Error reading " + myFileName + "\n");
       myErrorFlags |= READERROR;
     }

     return s;
   }

   /**
    *  Reads the next integer (without validating its format)
    *  @return the integer read or 0 if trying to read beyond the EOF
    */
   public int readInt()
   {
     String s = readWord();
     if (s != null)
       return Integer.parseInt(s);
     else
       return 0;
   }

   /**
    *  Reads the next double (without validating its format)
    *  @return the number read or 0 if trying to read beyond the EOF
    */
   public double readDouble()
   {
     String s = readWord();
     if (s != null)
       return Double.parseDouble(s);
       // in Java 1, use: return Double.valueOf(s).doubleValue();
     else
       return 0.0;
   }
 }