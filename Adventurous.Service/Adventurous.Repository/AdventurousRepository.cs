using Adventurous.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Adventurous.Repository
{
    public class AdventurousRepository
    {
        private AdventurousContext _context;

        public AdventurousRepository()
        {
            _context = new AdventurousContext();
        }    

        public Point[] GetPoints()
        {
            return _context.Points.ToArray();
        }

        public void SavePoint(Point point)
        {
            _context.Points.Add(point);
            _context.SaveChanges();
        }

        public void DeletePoint(Guid id)
        {
            _context.Points.Remove(_context.Points.Single(x => x.Id == id));
            _context.SaveChanges();
        }
    }
}
