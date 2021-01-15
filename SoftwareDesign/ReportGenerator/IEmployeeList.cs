using System.Collections.Generic;

namespace ReportGenerator
{
    public interface IEmployeeList
    {
         IEnumerable<Employee> GetEmployees();
    }
}