using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Moq;
using TimingSystem.Api.Controllers;
using TimingSystem.Domain.Abstractions;
using TimingSystem.Domain.Models;
using Xunit;

namespace Tests
{
    public class RaceControllerTests
    {
        [Fact]
        public void StartRace_ReturnsOkObjectResult()
        {
            // Arrange
            var mockLogger = new Mock<ILogger<RaceController>>();
            var mockRaceService = new Mock<IRaceService>();
            var controller = new RaceController(mockRaceService.Object, mockLogger.Object);

            var fastestLap = new RaceResult
            {
                Kart = 1,
                LapDuration = TimeSpan.FromSeconds(60),
                LapNumber = 5,
                LapStartTime = new DateTime(2024, 7, 18, 10, 15, 0),
                LapEndTime = new DateTime(2024, 7, 18, 10, 16, 0)
            };
            mockRaceService.Setup(x => x.GetFastestLap()).Returns(fastestLap);

            // Act
            var result = controller.StartRace();

            // Assert
            var okResult = Assert.IsType<OkObjectResult>(result);
            var model = Assert.IsType<RaceResult>(okResult.Value);
            Assert.Equal(fastestLap.Kart, model.Kart);
            Assert.Equal(fastestLap.LapDuration, model.LapDuration);
            Assert.Equal(fastestLap.LapNumber, model.LapNumber);
            Assert.Equal(fastestLap.LapStartTime, model.LapStartTime);
            Assert.Equal(fastestLap.LapEndTime, model.LapEndTime);

            mockLogger.Verify(
                x => x.LogInformation(It.Is<string>(s => s.Contains($"The winner is:{fastestLap.Kart}"))),
                Times.Once);
            mockLogger.VerifyNoOtherCalls();
        }
    }
}
