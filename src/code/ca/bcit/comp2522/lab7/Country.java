package ca.bcit.comp2522.lab7;

/**
 * Represents a country with a name.
 * Ensures that the name of the country is non-null and non-blank.
 * Provides methods to retrieve the country name and its string representation.
 * Example usage:
 * <pre>
 *     Country canada = new Country("Canada");
 *     System.out.println(canada.getName()); // Output: Canada
 *     System.out.println(canada);           // Output: Canada
 * </pre>
 *
 * @author Linh Hoang
 * @author Pouyan Norouzi Iranzadeh
 * @version 1.0
 */
public class Country
{
   private final String name;

   /**
    * Constructs a Country instance with the specified name.
    *
    * @param name the name of the country, must be non-null and not blank
    * @throws IllegalArgumentException if the name is null or blank
    */
   public Country(final String name)
   {
      validateName(name);

      this.name = name;
   }

   /**
    * Returns the name of the country.
    *
    * @return the name of the country
    */
   public String getName()
   {
      return name;
   }

   @Override
   public String toString()
   {
      return this.name;
   }

   private static void validateName(final String name)
   {
      if(name == null || name.isBlank())
      {
         throw new IllegalArgumentException("Name must not be empty!");
      }
   }
}