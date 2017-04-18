using Adventurous.Repository;
using System.Web.Http;

namespace Adventurous.Server.Controllers
{
    public class PointsController : ApiController
    {
        private AdventurousRepository _repository = new AdventurousRepository();

        public IHttpActionResult Get()
        {
            return Ok(_repository.GetPoints());
        }
    }
}
