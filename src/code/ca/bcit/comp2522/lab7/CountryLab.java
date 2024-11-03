package ca.bcit.comp2522.lab7;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The CountryLab class provides various methods to manipulate and analyze a list of Country objects.
 * It reads country names from a file, filters them according to specific criteria, and writes
 * results to an output file. This class demonstrates the use of streams, file I/O, and Java collections.
 * <p>
 * Example usage:
 * <pre>
 *     CountryLab.main(new String[]{});
 * </pre>
 * </p>
 * <p>
 * The output file will contain various statistics about the countries, such as countries with long names,
 * countries sorted alphabetically, and countries with names containing specific substrings.
 * </p>
 *
 * @author Linh Hoang
 * @author Pouyan Norouzi Iranzadeh
 * @version 1.0
 */
public class CountryLab
{
   /**
    * Entry point of the CountryLab application. Reads country names from an input file, performs
    * various operations on the list of countries, and writes the results to an output file.
    *
    * @param args command-line arguments (not used in this program)
    */
   public static void main(final String[] args)
   {
      final List<Country> countriesList;
      final Path inputPath;
      final Path outputPath;
      final Path rootDirPath;
      final Path subDirPath;

      countriesList = new ArrayList<>();
      inputPath     = Paths.get("src", "resources", "week8countries.txt");
      rootDirPath   = Paths.get("src");
      subDirPath    = Paths.get("src", "matches");

      try(final BufferedReader reader = Files.newBufferedReader(inputPath))
      {
         String line;

         while((line = reader.readLine()) != null)
         {
            countriesList.add(new Country(line));
         }
      } catch(final IOException e)
      {
         System.out.println("Error reading file! " + e.getMessage());
      }

      outputPath = createOutputFile(rootDirPath, subDirPath, "data.txt");

      writeLongCountryName(outputPath, countriesList);
      writeShortCountryNames(outputPath, countriesList);
      writeCountryNameStartsWithA(outputPath, countriesList);
      writeCountryNameEndingWithLand(outputPath, countriesList);
      writeCountryNamesContainingUnited(outputPath, countriesList);
      writeInAscendingOrder(outputPath, countriesList);
      writeInDescendingOrder(outputPath, countriesList);
      writeUniqueFirstLetters(outputPath, countriesList);
      writeTotalCountryNames(outputPath, countriesList);
      writeLongestCountryName(outputPath, countriesList);
      writeShortestCountryName(outputPath, countriesList);
      writeCountryNameInUpperCase(outputPath, countriesList);
      writeCountriesWithMoreThanOneWord(outputPath, countriesList);
      writeCharacterCount(outputPath, countriesList);
      writeAnyNameStartsWithZ(outputPath, countriesList);
      writeIsAllNamesLongerThan3(outputPath, countriesList);
   }

   private static Path createOutputFile(final Path rootDirPath,
                                        final Path subDirPath,
                                        final String fileName)
   {
      final Path outputPath;
      final Path subDirPathResolved;

      if(Files.exists(rootDirPath))
      {
         System.out.println("Folder exists! " + rootDirPath);
      } else
      {
         try
         {
            Files.createDirectory(rootDirPath);
         } catch (final IOException e)
         {
            System.out.println("Error creating folder! " + rootDirPath);
         }
      }

      subDirPathResolved = rootDirPath.resolve("matches");

      if(Files.exists(subDirPathResolved))
      {
         System.out.println("Subfolder already exists!");
      } else
      {
         try
         {
            Files.createDirectories(subDirPathResolved);
         } catch (final IOException e)
         {
            System.out.println("Error creating subfolder " + subDirPathResolved);
         }
      }

      outputPath = subDirPath.resolve(fileName);

      if(Files.exists(outputPath))
      {
         System.out.println("File already exists.");
         try
         {
            Files.delete(outputPath);
            Files.createFile(outputPath);
         } catch (final IOException e)
         {
            System.out.println("Could not delete and create the file again: " + e.getMessage());
         }
      } else
      {
         try
         {
            Files.createFile(outputPath);
            System.out.println("File created. " + outputPath);
         } catch (final IOException e)
         {
            System.out.println("Error creating file. " + e.getMessage());
         }
      }

      return outputPath;
   }

   /**
    * Filters out null Country objects and those with null or blank names from the given list.
    *
    * @param countries the list of Country objects to be filtered
    * @return a Stream of Country objects with valid names
    */
   private static Stream<Country> filteredCountries(final List<Country> countries)
   {
      return countries.stream()
              .filter(Objects::nonNull)
              .filter(c -> c.getName() != null)
              .filter(c -> !c.getName().isBlank());
   }

   /**
    * Writes country names longer than 10 characters to the specified output file.
    *
    * @param outputPath the path of the output file
    * @param countries  the list of Country objects to filter
    */
   @SuppressWarnings("checkstyle:SeparatorWrap")
   private static void writeLongCountryName(final Path outputPath, final List<Country> countries)
   {
      final List<String>  filteredCountryNames;

      filteredCountryNames = new ArrayList<>();
      filteredCountries(countries)
              .filter(c -> c.getName().length() > 10)
              .forEach(c -> filteredCountryNames.add(c.getName()));

      try
      {
         Files.writeString(outputPath, "********* Long country name ***********" +
                           System.lineSeparator(),
                           StandardOpenOption.CREATE, StandardOpenOption.APPEND);
         Files.write(outputPath, filteredCountryNames, StandardOpenOption.APPEND);
      } catch(final IOException e)
      {
         System.out.println("Error writing country name longer than 10 chars, " + e.getMessage());
      }
   }

   /**
    * Writes country names shorter than 5 characters to the specified output file.
    *
    * @param outputPath the path of the output file
    * @param countries  the list of Country objects to filter
    */
   private static void writeShortCountryNames(final Path outputPath, final List<Country> countries)
   {
      final List<String> filteredCountryNames;
      final Stream<Country> countryStream;

      filteredCountryNames = new ArrayList<>();
      countryStream = filteredCountries(countries);

      countryStream.filter(c -> c.getName().length() < 5).forEach(c -> filteredCountryNames.add(c.getName()));

      try
      {
         Files.writeString(outputPath,
                 System.lineSeparator() + "*******Short Country Names*******" + System.lineSeparator(),
                 StandardOpenOption.APPEND);
         Files.write(outputPath, filteredCountryNames, StandardOpenOption.APPEND);
      } catch (final IOException e)
      {
         System.out.println("Error writing short country names: " + e.getMessage());
      }
   }

   /**
    * Writes country names that start with the letter 'A' to the specified output file.
    *
    * @param outputPath the path of the output file
    * @param countries  the list of Country objects to filter
    */
   private static void writeCountryNameStartsWithA(final Path outputPath, final List<Country> countries)
   {
      final List<String> countryNameStartsWithA;

      countryNameStartsWithA = new ArrayList<>();

      filteredCountries(countries)
              .filter(c -> c.getName().startsWith("A"))
              .forEach(c -> countryNameStartsWithA.add(c.getName()));

      try
      {
         Files.writeString(outputPath, System.lineSeparator() +
                           "********* Country Name Starts With 'A' ***********" +
                           System.lineSeparator(), StandardOpenOption.APPEND);
         Files.write(outputPath, countryNameStartsWithA, StandardOpenOption.APPEND);
      } catch(final IOException e)
      {
         System.out.println("Error writing country name starting with 'A' to file. " + e.getMessage());
      }
   }

   /**
    * Writes country names ending with "land" to the specified output file.
    *
    * @param outputPath the path of the output file
    * @param countries  the list of Country objects to filter
    */
   private static void writeCountryNameEndingWithLand(final Path outputPath, final List<Country> countries)
   {
      final List<String> countryNameEndsWithLand;

      countryNameEndsWithLand = new ArrayList<>();

      filteredCountries(countries)
              .filter(c -> c.getName().endsWith("land"))
              .forEach(c -> countryNameEndsWithLand.add(c.getName()));

      try
      {
         Files.writeString(outputPath, System.lineSeparator() +
                           "********* Country name ends with 'land' ***********" +
                           System.lineSeparator(), StandardOpenOption.APPEND);
         Files.write(outputPath, countryNameEndsWithLand, StandardOpenOption.APPEND);
      } catch(final IOException e)
      {
         System.out.println("Error writing country name ending with 'land' " + e.getMessage());
      }
   }

   /**
    * Writes country names containing the substring "united" (case-insensitive) to the specified output file.
    *
    * @param outputPath the path of the output file
    * @param countries  the list of Country objects to filter
    */
   private static void writeCountryNamesContainingUnited(final Path outputPath, final List<Country> countries)
   {
      final List<String> filteredCountryNames;
      final Stream<Country> countryStream;

      filteredCountryNames = new ArrayList<>();
      countryStream = filteredCountries(countries);

      countryStream.filter(c -> c.getName().toLowerCase().contains("united"))
              .forEach(c -> filteredCountryNames.add(c.getName()));

      try
      {
         Files.writeString(outputPath,
                 System.lineSeparator() + "*******Countries Containing United*******" + System.lineSeparator(),
                 StandardOpenOption.APPEND);
         Files.write(outputPath, filteredCountryNames, StandardOpenOption.APPEND);
      } catch (final IOException e)
      {
         System.out.println("Error writing countries containing united: " + e.getMessage());
      }
   }

   /**
    * Writes country names sorted in ascending order to the specified output file.
    *
    * @param outputPath the path of the output file
    * @param countries  the list of Country objects to sort
    */
   private static void writeInAscendingOrder(final Path outputPath, final List<Country> countries)
   {
      final List<String> filteredCountryNames;
      final Stream<Country> countryStream;

      filteredCountryNames = new ArrayList<>();
      countryStream = filteredCountries(countries);

      countryStream.sorted(Comparator.comparing(Country::getName))
              .forEachOrdered(c -> filteredCountryNames.add(c.getName()));

      try
      {
         Files.writeString(outputPath,
                 System.lineSeparator() + "*******Countries Sorted By Name (ASC)*******" + System.lineSeparator(),
                 StandardOpenOption.APPEND);
         Files.write(outputPath, filteredCountryNames, StandardOpenOption.APPEND);
      } catch (final IOException e)
      {
         System.out.println("Error sorting by name ascending: " + e.getMessage());
      }
   }

   /**
    * Writes country names sorted in descending order to the specified output file.
    *
    * @param outputPath the path of the output file
    * @param countries  the list of Country objects to sort
    */
   private static void writeInDescendingOrder(final Path outputPath, final List<Country> countries)
   {
      final List<String> descendingSortedCountries;

      descendingSortedCountries = new ArrayList<>();

      filteredCountries(countries)
              .sorted(Comparator.comparing(Country::getName).reversed())
              .forEachOrdered(c -> descendingSortedCountries.add(c.getName()));

      try
      {
         Files.writeString(outputPath, System.lineSeparator() +
                           "********* Sorted Country Name (DSC) *********" +
                           System.lineSeparator(), StandardOpenOption.APPEND);
         Files.write(outputPath, descendingSortedCountries, StandardOpenOption.APPEND);
      } catch(final IOException e)
      {
         System.out.println("Error writing sorted country names to file. " + e.getMessage());
      }
   }

   /**
    * Writes unique first letters of country names to the specified output file.
    *
    * @param outputPath the path of the output file
    * @param countries  the list of Country objects to filter
    */
   private static void writeUniqueFirstLetters(final Path outputPath, final List<Country> countries)
   {
      final List<String> filteredCountryNames;
      final Stream<Country> countryStream;
      final Map<Character, List<Country>> countryMap;

      filteredCountryNames = new ArrayList<>();
      countryStream = filteredCountries(countries);

      countryMap = countryStream.collect(Collectors.groupingBy(c -> c.getName().toUpperCase().charAt(0)));

      countryMap.forEach((k, c) ->
      {
         if(c.size() == 1)
         {
            filteredCountryNames.add(c.getFirst().getName());
         }
      });

      try
      {
         Files.writeString(outputPath, System.lineSeparator() +
                 "********* Unique First Letters *********" +
                 System.lineSeparator(), StandardOpenOption.APPEND);
         Files.write(outputPath, filteredCountryNames, StandardOpenOption.APPEND);
      } catch(final IOException e)
      {
         System.out.println("Error writing sorted country names to file. " + e.getMessage());
      }
   }

   /**
    * Writes the total number of country names to the specified output file.
    *
    * @param outputPath the path of the output file
    * @param countries  the list of Country objects to count
    */
   private static void writeTotalCountryNames(final Path outputPath, final List<Country> countries)
   {
      try
      {
         Files.writeString(outputPath, System.lineSeparator() +
                 "********* Total country names: " +
                 filteredCountries(countries).count() +
                 System.lineSeparator(),
                 StandardOpenOption.APPEND);
      } catch(final IOException e)
      {
         System.out.println("Error counting and writing total country name. " + e.getMessage());
      }
   }

   /* ASK JASON */
   /**
    * Writes the longest country name to the specified output file.
    *
    * @param outputPath the path of the output file
    * @param countries  the list of Country objects to filter
    */
   private static void writeLongestCountryName(final Path outputPath, final List<Country> countries)
   {
      filteredCountries(countries)
              .max(Comparator.comparing(c -> c.getName().length()))
              .ifPresent(longestName ->
              {
                 try
                 {
                    Files.writeString(outputPath, System.lineSeparator() +
                            "Longest country name: " + longestName +
                            System.lineSeparator(), StandardOpenOption.APPEND);
                 } catch(final IOException e)
                 {
                    System.out.println("Error writing longest country name to file. " + e.getMessage());
                 }
              });
   }

   /**
    * Writes the shortest country name to the specified output file.
    *
    * @param outputPath the path of the output file
    * @param countries  the list of Country objects to filter
    */
   private static void writeShortestCountryName(final Path outputPath, final List<Country> countries)
   {
      filteredCountries(countries)
              .min(Comparator.comparing(c -> c.getName().length()))
              .ifPresent(shortestName ->
              {
                 try
                 {
                    Files.writeString(outputPath, System.lineSeparator() +
                                             "Shortest Country Name: " + shortestName +
                                             System.lineSeparator(),
                                             StandardOpenOption.APPEND);

                 } catch(final IOException e)
                 {
                    System.out.println("Error writing shortest country name to file " + e.getMessage());
                 }
              });
   }

   /**
    * Writes country names in uppercase to the specified output file.
    *
    * @param outputPath the path of the output file
    * @param countries  the list of Country objects to convert
    */
   private static void writeCountryNameInUpperCase(final Path outputPath, final List<Country> countries)
   {
      try
      {
         Files.writeString(outputPath, System.lineSeparator() +
                 "******** Country Name in UPPERCASE ********" +
                 System.lineSeparator(), StandardOpenOption.APPEND);
         filteredCountries(countries)
                 .forEach(c ->
                 {
                    try
                    {
                       Files.writeString(outputPath, c.getName().toUpperCase() +
                                       System.lineSeparator(),
                               StandardOpenOption.APPEND);
                    } catch(final IOException e)
                    {
                       System.out.println("Error writing country names in UPPERCASE to file" + e.getMessage());
                    }

                 });
      } catch(final IOException e)
      {
         System.out.println("Error writing country names in UPPERCASE to file " + e.getMessage());
      }
   }

   /**
    * Writes country names that contain more than one word to the specified output file.
    *
    * @param outputPath the path of the output file
    * @param countries  the list of Country objects to filter
    */
   private static void writeCountriesWithMoreThanOneWord(final Path outputPath, final List<Country> countries)
   {
      final List<String> filteredCountryNames;
      final Stream<Country> countryStream;

      filteredCountryNames = new ArrayList<>();
      countryStream = filteredCountries(countries);

      countryStream.filter(c -> c.getName().contains(" ")).forEach(c -> filteredCountryNames.add(c.getName()));

      try
      {
         Files.writeString(outputPath,
                 System.lineSeparator() + "*******Countries With Multiple Words*******" + System.lineSeparator(),
                 StandardOpenOption.APPEND);
         Files.write(outputPath, filteredCountryNames, StandardOpenOption.APPEND);
      } catch (final IOException e)
      {
         System.out.println("Error writing countries with multiple words: " + e.getMessage());
      }
   }

   /**
    * Writes each country name and its character count to the specified output file.
    *
    * @param outputPath the path of the output file
    * @param countries  the list of Country objects to count characters
    */
   private static void writeCharacterCount(final Path outputPath, final List<Country> countries)
   {
      final Map<String, Integer> countryMap;
      final Stream<Country> countryStream;

      countryMap = new HashMap<>();
      countryStream = filteredCountries(countries);

      countryStream.forEach(c -> countryMap.put(c.getName(), c.getName().length()));

      try
      {
         Files.writeString(outputPath,
                 System.lineSeparator() + "*******Countries and Character Counts*******" + System.lineSeparator(),
                 StandardOpenOption.APPEND);
      } catch (final IOException e)
      {
         System.out.println("Error when writing char count to file: " + e.getMessage());
      }


      countryMap.forEach((c, l) ->
      {
         try
         {
            final String str = String.format("%s: %d characters", c, l) +
                    System.lineSeparator();
            Files.writeString(outputPath, str, StandardOpenOption.APPEND);
         } catch (final IOException e)
         {
            System.out.println("Error when writing char count to file: " + e.getMessage());
         }
      });
   }

   /**
    * Writes whether any country name starts with 'Z' to the specified output file.
    *
    * @param outputPath the path of the output file
    * @param countries  the list of Country objects to check
    */
   private static void writeAnyNameStartsWithZ(final Path outputPath, final List<Country> countries)
   {
      final boolean anyStartsWithZ;

      anyStartsWithZ = filteredCountries(countries)
                                    .anyMatch(c -> c.getName()
                                    .toLowerCase().startsWith("z"));

      try
      {
         Files.writeString(outputPath, System.lineSeparator() +
                           "Any country name starts with 'z': " + anyStartsWithZ +
                           System.lineSeparator(),
                           StandardOpenOption.APPEND);

      } catch(final IOException e)
      {
         System.out.println("Error checking and writing any " +
                              "country name starting with 'z' " + e.getMessage());
      }
   }

   /**
    * Writes whether all country names are longer than 3 characters to the specified output file.
    *
    * @param outputPath the path of the output file
    * @param countries  the list of Country objects to check
    */
   private static void writeIsAllNamesLongerThan3(final Path outputPath,
                                                final List<Country> countries)
   {
      final boolean isAllCountryNameLongerThan3;
      
      isAllCountryNameLongerThan3 = filteredCountries(countries).allMatch(c -> c.getName().length() > 3);

      try
      {
         Files.writeString(outputPath, System.lineSeparator() +
                           "Are all country names longer than 3 characters: " +
                           isAllCountryNameLongerThan3 + System.lineSeparator(),
                           StandardOpenOption.APPEND);
      } catch(final IOException e)
      {
         System.out.println("Error checking and writing whether all country names " +
                 "are longer than 3 characters " + e.getMessage());
      }
   }
}
