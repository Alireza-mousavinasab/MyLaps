using CsvHelper;
using Microsoft.Extensions.Logging;
using System.Globalization;
using TimingSystem.Domain.Abstractions;
using TimingSystem.Domain.Models;

namespace TimingSystem.DAL
{
    public class KartLapRepository : IKartLapRepository
    {
        private readonly ILogger<KartLapRepository> _logger;

        public KartLapRepository(ILogger<KartLapRepository> logger)
        {
            _logger = logger;
        }

        public List<KartLap> GetKartLaps()
        {
            var filePath = Path.Combine(Directory.GetCurrentDirectory(), "karttimes.csv");
            if (!File.Exists(filePath))
            {
                _logger.LogError("CSV file doesn't exist to fetch the data!");
                return new List<KartLap>();
            }

            using (var reader = new StreamReader(filePath))
            using (var csv = new CsvReader(reader, new CsvHelper.Configuration.CsvConfiguration(CultureInfo.InvariantCulture)
            {
                HasHeaderRecord = true,
            }))
            {
                var records = csv.GetRecords<dynamic>().ToList();
                var laps = new List<KartLap>();
                foreach (var record in records)
                {
                    laps.Add(new KartLap
                    {
                        Kart = int.Parse(record.kart),
                        PassingTime = DateTime.Parse(record.passingtime)
                    });
                }
                _logger.LogInformation($"Data has been loaded with {laps.Count} records!");
                return laps;
            }
        }
    }
}
