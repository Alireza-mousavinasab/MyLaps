using Microsoft.Extensions.Logging;
using TimingSystem.Domain.Abstractions;
using TimingSystem.Domain.Models;

namespace TimingSystem.Service
{
    public class RaceService : IRaceService
    {
        private readonly IKartLapRepository _repo;
        private readonly ILogger<RaceService> _logger;

        private List<KartLap> laps = new List<KartLap>();

        public RaceService(IKartLapRepository repo, ILogger<RaceService> logger)
        {
            _repo = repo;
            _logger = logger;
        }

        public void StartRace()
        {
            this.laps.Clear();
            this.laps = _repo.GetKartLaps();
            _logger.LogInformation("Race has been started!");
        }

        public RaceResult GetFastestLap()
        {
            try
            {
                var fastestKart = laps
                   .GroupBy(k => k.Kart)
                   .Where(g => g.Count() == 5)
                   .Select(g =>
                   {
                       var orderedLaps = g.OrderBy(l => l.PassingTime).ToList();
                       var lapDurations = new List<TimeSpan>();

                       for (int i = 1; i < orderedLaps.Count; i++)
                       {
                           lapDurations.Add(orderedLaps[i].PassingTime - orderedLaps[i - 1].PassingTime);
                       }

                       return new RaceResult
                       {
                           Kart = g.Key,
                           LapDuration = lapDurations.Aggregate(TimeSpan.Zero, (subtotal, t) => subtotal.Add(t)),
                           LapNumber = 5,
                           LapStartTime = orderedLaps.FirstOrDefault()?.PassingTime,
                           LapEndTime = orderedLaps.LastOrDefault()?.PassingTime
                       };
                   })
                   .OrderBy(g => g.LapDuration)
                   .FirstOrDefault();
                if (fastestKart != null)
                {
                    return fastestKart;
                }
                else
                {
                    _logger.LogWarning($"There is no data to get the fastest lap");
                    return new RaceResult();
                }
            }
            catch (Exception ex)
            {
                _logger.LogError($"There is an error in getting the fastest lap:" + ex.Message);
                return new RaceResult();
            }
        }
    }
}
