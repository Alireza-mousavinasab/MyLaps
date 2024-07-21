using TimingSystem.Domain.Models;

namespace TimingSystem.Domain.Abstractions
{
    public interface IKartLapRepository
    {
        List<KartLap> GetKartLaps();
    }
}
