Lab Exercise for Week 8: Stream Processing with Country Names




Objectives

1. Learn how to use NIO for file operations.

2. Practice various "Stream" operations, including "filter", "map", "reduce", and terminal operations.

3. Gain hands-on experience with Java’s functional programming and NIO "Path" and "Files" classes.

Requirements

1. Read a text file named "week8countries.txt", where each line contains the name of a country (the file was supplied along with this lab writeup document).

2. If the subdirectory "matches" does not exist, create it.

3. Use "Stream" and "filter" operations on the list of countries to meet specific criteria.

4. Write results into a file "data.txt" within the "matches" directory.

5. Create a File: Check if "matches" directory exists; if not, create it.

6. Read the File: Read all lines from "countries.txt" into a list.

7. Write to "data.txt": For each of the 16 requirements below, use stream processing to filter, map, or reduce the list of countries as needed, and write the results to "data.txt" in the "matches" directory.




1. Long Country Names: Write "Country names longer than 10 characters:" followed by all country names with more than 10 characters (always one country per line).

2. Short Country Names: Write "Country names shorter than 5 characters:" followed by all country names with fewer than 5 characters.

3. Starting with "A": List all country names that start with the letter "A".

4. Ending with "land": List all country names that end with "land".

5. Containing "United": List all countries containing the word "United".

6. Sorted Names (Ascending): List all country names in alphabetical order.

7. Sorted Names (Descending): List all country names in reverse alphabetical order.

8. Unique First Letters: List the unique first letters of all country names.

9. Count of Countries: Write the total count of country names.

10. Longest Country Name: Write the longest country name.

11. Shortest Country Name: Write the shortest country name.

12. Names in Uppercase: Write all country names converted to uppercase.

13. Countries with More Than One Word: List all country names with more than one word.

14. Country Names to Character Count: Map each country name to its character count, writing each name and count as "Country: X characters".

15. Any Name Starts with "Z": Write "true" if any country name starts with "Z"; otherwise, "false".

16. All Names Longer Than 3: Write "true" if all country names are longer than 3 characters; otherwise, "false".







Lab Instructions

1. Setup: Ensure you have the file named "lesson8countries.txt" in the same directory as this code.

2. Run the Code: Compile and run the "CountryLab" class.

3. Output: After running, check the "matches/data.txt" file. It should contain the results of all 16 requirements.

4. Pass the unit tests in the Lesson8UnitTest.java file.

Shape2Shape1

2

