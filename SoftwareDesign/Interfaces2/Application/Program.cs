using System;
using Vehicles;

namespace Application {
    class Program {
        static void Main (string[] args) {
            MotorBike bike = new MotorBike (new GasEngine(100));

        }
    }
}