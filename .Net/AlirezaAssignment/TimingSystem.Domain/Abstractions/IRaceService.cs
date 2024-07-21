using TimingSystem.Domain.Models;

namespace TimingSystem.Domain.Abstractions
{
    public interface IRaceService
    {
        void StartRace();
        RaceResult GetFastestLap();
    }
}
