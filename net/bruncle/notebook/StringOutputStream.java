package net.bruncle.notebook;
import java.io.OutputStream;

public class StringOutputStream extends OutputStream 
{
   private String sink = "";

   public StringOutputStream()
   {
      super();
   }

   public void write( int b )
   {
      char c = ( char ) b;
      sink = sink + c;
   }

   public String getSink()
   {
      return sink;
   }
}