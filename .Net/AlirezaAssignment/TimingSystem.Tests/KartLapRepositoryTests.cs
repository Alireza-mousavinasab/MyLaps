using Microsoft.Extensions.Logging;
using Moq;
using TimingSystem.DAL;

namespace TimingSystem.Tests
{
    public class KartLapRepositoryTests
    {
        [Fact]
        public void GetKartLaps_WhenCsvFileExists_ReturnsLaps()
        {
            // Arrange
            var csvFilePath = Path.Combine(Directory.GetCurrentDirectory(), "karttimes.csv");
            Assert.True(File.Exists(csvFilePath), $"CSV file '{csvFilePath}' does not exist.");

            var repository = new KartLapRepository(new Logger<KartLapRepository>(new LoggerFactory())); // Instantiate repository with appropriate constructor

            // Act
            var laps = repository.GetKartLaps();

            // Assert
            Assert.NotNull(laps); // Ensure laps is not null
            Assert.NotEmpty(laps); // Ensure laps contains data
            Assert.Equal(25, laps.Count); // Ensure correct number of laps

            // Optionally, add more specific assertions about the content of laps
            Assert.Equal(1, laps.First().Kart);
            Assert.Equal(new DateTime(2024, 7, 19, 12, 0, 0), laps.First().PassingTime);
        }
    }
}
