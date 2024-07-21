using Microsoft.Extensions.Logging;
using Moq;
using TimingSystem.DAL;
using Xunit;

namespace Tests
{
    public class KartLapRepositoryTests
    {
        [Fact]
        public void GetKartLaps_WhenCsvFileExists_ReturnsLaps()
        {
            // Arrange
            var mockLogger = new Mock<ILogger<KartLapRepository>>();
            var repository = new KartLapRepository(mockLogger.Object);

            // Act
            var laps = repository.GetKartLaps();

            // Assert
            Assert.NotNull(laps);
            Assert.NotEmpty(laps);
            Assert.Equal(25, laps.Count); // Adjust expected count based on your CSV file

            // Add more assertions based on your CSV file data and expected content
            // For example:
            Assert.Equal(1, laps.First().Kart);
            Assert.Equal(new DateTime(2024, 7, 18, 10, 15, 0), laps.First().PassingTime); // Adjust DateTime based on your CSV format
                                                                                          // Ensure logging was called appropriately
            mockLogger.Verify(x => x.LogInformation(It.IsAny<string>()), Times.AtLeastOnce);
            mockLogger.VerifyNoOtherCalls();
        }
    }
}
