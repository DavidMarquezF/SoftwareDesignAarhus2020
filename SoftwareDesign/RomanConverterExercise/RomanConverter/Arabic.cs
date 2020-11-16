using System.Text;
using System.Collections.Generic;
namespace RomanConverter
{
    public class Arabic
    {
        private readonly int _num;
        SortedList<int, char> romanDict = new SortedList<int, char>()
        {
            {1000, 'M'},
            {500, 'D'},
            {100, 'C'},
            {50, 'L'},
            {10, 'X'},
            {5, 'V'},
            {1, 'I'}
        };
        public Arabic(int number)
        {
            _num = number;
        }

        // https://gist.github.com/ajcomeau/fdd46f8ee87f30944e3524d2d8181c36
        public string ToRoman()
        {
            string returnValue = "";
            int num = _num;

            int dictionaryElement = romanDict.Count - 1;

            char romanChar = romanDict.Values[dictionaryElement];
            int arabicNumber = romanDict.Keys[dictionaryElement];
            var arabicSubLevel = arabicNumber - ((arabicNumber.ToString()[0] == '1') ? (arabicNumber / 10) : (arabicNumber / 5));
            while (num != 0)
            {
                if (num >= arabicNumber) // If the number remains above the current test.
                {
                    // If the current Roman numeral ends with three of the current Roman character,
                    // and the current Arabic number starts with 1, remove the three characters and
                    // add the subtractive notation (i.e. III to IV and XXXVIII to XXXIX)
                    if (returnValue.EndsWith(new string(romanChar, 3)) && arabicNumber.ToString()[0] == '1')
                    {
                        returnValue = returnValue.Substring(0, returnValue.Length - 3);
                        returnValue += romanChar;
                        returnValue += romanDict.Values[dictionaryElement + 1];
                    }
                    else // Otherwise, just add another character.
                    {
                        returnValue += romanDict.Values[dictionaryElement];
                    }

                    // Subtract the amount that has been added to the Roman numeral.
                    num -= arabicNumber;
                }
                else if (num >= arabicSubLevel)
                {
                    // If the number is less than the current level but greater than the sublevel
                    // (i.e. less than 1000 but 900 or greater), add the appropriate letters.

                    if (arabicNumber.ToString()[0] == '1')
                    {
                        returnValue += romanDict.Values[dictionaryElement - 2];
                    }
                    else
                    {
                        returnValue += romanDict.Values[dictionaryElement - 1];
                    }

                    returnValue += romanDict.Values[dictionaryElement];

                    // Subtract the amount that has been added to the Roman numeral.
                    num -= arabicSubLevel;
                }
                else
                {
                    // Otherwise, move forward in the dictionary and get the new values.
                    dictionaryElement--;
                    arabicNumber = romanDict.Keys[dictionaryElement];
                    romanChar = romanDict.Values[dictionaryElement];
                    arabicSubLevel = arabicNumber - ((arabicNumber.ToString()[0] == '1') ? (arabicNumber / 10) : (arabicNumber / 5));
                }
            }
            return returnValue;
        }
    }
}