using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Adventurous.Models
{
    public class Point
    {
        public Guid Id { private set; get; }
        public double Latitude { set; get; }
        public double Longitude { set; get; }
        public string Clue { private set; get; }
        public string[] Hints
        {
            private set
            {
                InternalHints = String.Join(";", value);
            }
            get
            {
                return Array.ConvertAll(InternalHints.Split(';'), String.Copy);
            }
        }
        public string InternalHints { private set; get; }
        public string[] Answers
        {
            private set
            {
                InternalAnswers = String.Join(";", value);
            }
            get
            {
                return Array.ConvertAll(InternalAnswers.Split(';'), String.Copy);
            }
        }
        public string InternalAnswers { private set; get; }
        public int RigthAnswerId { private set; get; }
        public bool IsFree { private set; get; }
        public Point()
        {
        }
        public Point(string clue, double latitude, double longitude, string[] hints, string[] answers, int rigthAnswerId, bool isFree = true)
        {
            Id = Guid.NewGuid();
            Clue = clue;
            Latitude = latitude;
            Longitude = longitude;
            Hints = hints;
            Answers = answers;
            RigthAnswerId = rigthAnswerId;
            IsFree = isFree;
        }
    }
}
