using Microsoft.Extensions.Logging;
using Moq;
using System.Reflection;
using TimingSystem.Domain.Abstractions;
using TimingSystem.Domain.Models;
using TimingSystem.Service;
using Xunit;

namespace Tests
{
    public class RaceServiceTests
    {
        [Fact]
        public void StartRace_ClearsAndLoadsLaps()
        {
            // Arrange
            var mockLogger = new Mock<ILogger<RaceService>>();
            var mockRepository = new Mock<IKartLapRepository>();
            mockRepository.Setup(x => x.GetKartLaps()).Returns(new List<KartLap>
            {
                new KartLap { Kart = 1, PassingTime = new DateTime(2024, 7, 18, 10, 15, 0) },
                new KartLap { Kart = 2, PassingTime = new DateTime(2024, 7, 18, 10, 16, 0) },
                new KartLap { Kart = 3, PassingTime = new DateTime(2024, 7, 18, 10, 17, 0) },
                new KartLap { Kart = 4, PassingTime = new DateTime(2024, 7, 18, 10, 18, 0) },
                new KartLap { Kart = 5, PassingTime = new DateTime(2024, 7, 18, 10, 19, 0) },
            });

            var raceService = new RaceService(mockRepository.Object, mockLogger.Object);

            // Act
            raceService.StartRace();

            // Assert
            var lapsField = typeof(RaceService).GetField("laps", BindingFlags.Instance | BindingFlags.NonPublic)?.GetValue(raceService) as List<KartLap>;
            Assert.NotNull(lapsField);
            Assert.NotEmpty(lapsField);
            Assert.Equal(25, lapsField.Count); // Adjust expected count based on mock data
            mockLogger.Verify(x => x.LogInformation(It.IsAny<string>()), Times.Once);
            mockLogger.VerifyNoOtherCalls();
        }

        [Fact]
        public void GetFastestLap_ReturnsFastestLap()
        {
            // Arrange
            var mockLogger = new Mock<ILogger<RaceService>>();
            var mockRepository = new Mock<IKartLapRepository>();
            var laps = new List<KartLap>
            {
                new KartLap { Kart = 1, PassingTime = new DateTime(2024, 7, 18, 10, 15, 0) },
                new KartLap { Kart = 2, PassingTime = new DateTime(2024, 7, 18, 10, 16, 0) },
                new KartLap { Kart = 3, PassingTime = new DateTime(2024, 7, 18, 10, 17, 0) },
                new KartLap { Kart = 4, PassingTime = new DateTime(2024, 7, 18, 10, 18, 0) },
                new KartLap { Kart = 5, PassingTime = new DateTime(2024, 7, 18, 10, 19, 0) }
            };
            mockRepository.Setup(x => x.GetKartLaps()).Returns(laps);

            var raceService = new RaceService(mockRepository.Object, mockLogger.Object);

            // Act
            raceService.StartRace();
            var fastestLap = raceService.GetFastestLap();

            // Assert
            Assert.NotNull(fastestLap);
            Assert.Equal(1, fastestLap.Kart);
            Assert.Equal(TimeSpan.FromMinutes(1), fastestLap.LapDuration); // Adjust expected lap duration based on your sample data
            mockLogger.Verify(x => x.LogInformation(It.IsAny<string>()), Times.AtLeastOnce);
            mockLogger.VerifyNoOtherCalls();
        }
    }
}
