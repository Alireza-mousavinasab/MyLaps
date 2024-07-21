using Microsoft.AspNetCore.Mvc;

namespace AlirezaAssignment.Controllers
{
    public class RaceController : Controller
    {
        public IActionResult StartRace()
        {
            return View();
        }
    }
}
