namespace TimingSystem.Domain.Models
{
    public class RaceResult
    {
        public int Kart { get; set; }
        public TimeSpan LapDuration { get; set; }
        public int LapNumber { get; set; }
        public DateTime? LapStartTime { get; set; }
        public DateTime? LapEndTime { get; set; }
    }
}
