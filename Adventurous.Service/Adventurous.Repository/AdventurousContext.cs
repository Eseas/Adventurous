using Adventurous.Models;
using System.Data.Entity;


namespace Adventurous.Repository
{
    public class AdventurousContext : DbContext
    {
        public AdventurousContext() : base("name=Adventurous.Database")
        {
        }

        public DbSet<Point> Points { set; get; }
    }
}
