package ca.bcit.comp2522.lab7;

public class Country
{
   private final String name;

   public Country(final String name)
   {
      validateName(name);

      this.name = name;
   }

   public String getName()
   {
      return name;
   }

   private static void validateName(final String name)
   {
      if(name == null || name.isBlank())
      {
         throw new IllegalArgumentException("Name must not be empty!");
      }
   }
}