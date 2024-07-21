using Microsoft.AspNetCore.Mvc;
using TimingSystem.Domain.Abstractions;

namespace TimingSystem.Api.Controllers
{
    /// <summary>
    /// RaceController handles API requests for race service.
    /// </summary>
    [ApiController]
    [Route("api/[controller]")]
    public class RaceController(IRaceService raceService, ILogger<RaceController> logger) : ControllerBase
    {
        /// <summary>
        /// Start Race
        /// </summary>
        /// <returns>The winner of match</returns>
        [HttpGet]
        public IActionResult StartRace()
        {
            raceService.StartRace();
            var fastestLap = raceService.GetFastestLap();
            logger.LogInformation("The winner is:" + fastestLap.Kart);

            return Ok(fastestLap);
        }
    }
}
