namespace Vehicles {
    public class MotorBike {
        private IEngine _engine = null;
        public MotorBike (IEngine engine) {
            _engine = engine;
        }
        void RunAtHalfSpeed () {
            _engine.SetThrottle (_engine.MaxThrottle / 2);
        }
    }
}