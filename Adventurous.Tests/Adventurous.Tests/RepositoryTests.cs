using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Adventurous.Repository;
using Adventurous.Models;

namespace Adventurous.Tests
{
    [TestClass]
    public class RepositoryTests
    {
        [TestMethod]
        public void TestMethod1()
        {
            AdventurousRepository sut = new AdventurousRepository();

            sut.SavePoint(new Point("Lukiškių aikštė", 54.689402, 25.270837,
                new[] { "Prieš mirtį Vytautui akyse mirgėjo", "Danguje pėdsakus mina", "Jei penktadienį 13-tą prieš akis juodas katinas prabėgų, TAI padėtų" },
                new[] { "Skydas", "Žiedas", "Kalavijas", "Kryžius", "Varnas", "Varpas", "Saulė", "Mėnulis", "Debesys", "Trispalvė", "Keturlapis dobilas", "Aukso puodas", "Policija", "Pasaga" },
                13));

            sut.SavePoint(new Point("Žemaitės skveras", 54.688493, 25.274018,
                new[] { "Koks vardas užrašytas ant skulptūros?" },
                new[] { "Aleksandravičius", "Petras", "Žemaitė", "Julija", "Vardo nėra", "Vėjas", "Marija", "Antanas", "Laurynas", "Julijana", "Saulė" },
                4));

            sut.SavePoint(new Point("Kudirkos aikštė", 54.687439, 25.280529,
                new[] { "Koks skulptūros pagrindo plotas?", "Plytelės kraštinės ilgis 1.3 metrai", "C=2πR", "S = πR^2" },
                new[] { "15–45", "46–75", "76–105", "106–135", "136–165", "166–195", "196–225", "226–255", "256–285", "286–315" },
                3, false));

            sut.SavePoint(new Point("Suktasis punktas", 54.687792, 25.276435,
                new[] { "Kiekvieną dieną TAI žmonės niekina šito nespastebėdami", "Kojom trypia", "Dangčius puošia" },
                new[] { "Vytis", "Kryžius", "Trispalvė", "Gedimino stulpai", "Dovydo skydai", "Zebro dryžiai", "Dievas", "Plytelės", "Žolė", "Vilniaus herbas" },
                9));

            //sut.DeletePoint(new Guid("A4B34477-2089-413D-B751-5D7BC8335A87"));

            //var test = sut.GetPoints();

        }
    }
}
