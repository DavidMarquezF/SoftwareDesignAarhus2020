using System;

namespace ReportGenerator
{
    internal class ReportGeneratorClient
    {
        private static void Main()
        {
            var db = new EmployeeDB();

            // Add some employees
            db.AddEmployee(new Employee("Anne", 3000, 18));
            db.AddEmployee(new Employee("Berit", 2000,20));
            db.AddEmployee(new Employee("Christel", 1000,45));

            Console.WriteLine("");
            Console.WriteLine("");
            ReportGenerator.CompileReport(new EmployeeHandler(db), new ConsoleOutputter(), new AgeFirstFormatter());
        }
    }
}