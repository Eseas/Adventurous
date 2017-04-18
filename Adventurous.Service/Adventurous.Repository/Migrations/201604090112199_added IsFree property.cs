namespace Adventurous.Repository.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class addedIsFreeproperty : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Points", "IsFree", c => c.Boolean(nullable: false, defaultValue: true));
        }
        
        public override void Down()
        {
            DropColumn("dbo.Points", "IsFree");
        }
    }
}
