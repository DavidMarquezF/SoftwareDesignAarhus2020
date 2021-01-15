using System;
using System.Collections.Generic;

namespace ReportGenerator
{
    public class EmployeeHandler : IEmployeeList
    {
        private EmployeeDB _employeeDb;

        public EmployeeHandler(EmployeeDB employeeDB){
            _employeeDb = employeeDB ?? throw new ArgumentNullException(nameof(employeeDB));
        }

        public IEnumerable<Employee> GetEmployees()
        {
            var employees = new List<Employee>();
            Employee employee;

            _employeeDb.Reset();

            // Get all employees
            while ((employee = _employeeDb.GetNextEmployee()) != null)
            {
                employees.Add(employee);
            }

            return employees;
        }
    }
}