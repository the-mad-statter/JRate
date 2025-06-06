package com.themadstatter.pathfinder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class EasyWriter
{
  protected String myFileName;
  protected PrintWriter myOutFile;
  protected int myErrorFlags = 0;
  protected static final int OPENERROR = 0x0001;
  protected static final int CLOSEERROR = 0x0002;
  protected static final int WRITEERROR = 0x0004;

  /**
   *  Constructor.  Creates a new file (or truncates an existing file)
   *  @param fileName the name of the file to be created
   */
  public EasyWriter(String fileName)
  {
    this(fileName, null);
  }

  /**
   *  Constructor.  Creates a new file. If the file exists
   *  can append to it.
   *  @param fileName the name of the file to be created
   *  @param mode if equals to "app" opens in append mode
   */
  public EasyWriter(String fileName, String mode)
  {
    myFileName = fileName;
    myErrorFlags = 0;

    try
    {
      myOutFile = new PrintWriter(
              new FileWriter(fileName, "app".equals(mode)));
    }
    catch (IOException e)
    {
      myErrorFlags |= OPENERROR;
      myFileName = null;
    }
  }

  /**
   *  Closes the file
   */
  public void close()
  {
    if (myFileName != null)
      myOutFile.close();
  }

  /**
   *  Checks the status of the file
   *  @return true if en error occurred opening or writing to the file,
   *  false otherwise
   */
  public boolean bad()
  {
    return myErrorFlags != 0;
  }

  /**
   *  Writes one character to the file
   *  @param ch character to be written
   */
  public void print(char ch)
  {
    myOutFile.print(ch);
  }

  /**
   *  Writes an integer to the file
   *  @param k number to be written
   */
  public void print(int k)
  {
    myOutFile.print(k);
  }

  /**
   *  Writes a double to the file
   *  @param x number to be written
   */
  public void print(double x)
  {
    myOutFile.print(x);
  }

  /**
   *  Writes a string to the file
   *  @param s string to be written
   */
  public void print(String s)
  {
    myOutFile.print(s);
  }

  /**
   *  Writes an object to the file
   *  @param obj object to be written
   */
  public void print(Object obj)
  {
    myOutFile.print(obj);
  }

  /**
   *  Writes a newline character to the file
   */
  public void println()
  {
    myOutFile.println();
  }

  /**
   *  Writes one character and newline to the file
   *  @param ch character to be written
   */
  public void println(char ch)
  {
    myOutFile.println(ch);
  }

  /**
   *  Writes an integer and newline to the file
   *  @param k number to be written
   */
  public void println(int k)
  {
    myOutFile.println(k);
  }

  /**
   *  Writes a double and newline to the file
   *  @param x number to be written
   */
  public void println(double x)
  {
    myOutFile.println(x);
  }

  /**
   *  Writes a string and newline to the file
   *  @param s string to be written
   */
  public void println(String s)
  {
    myOutFile.println(s);
  }

  /**
   *  Writes an object and newline to the file
   *  @param obj object to be written
   */
  public void println(Object obj)
  {
    myOutFile.println(obj);
  }
}