module.exports = (sequelize, DataTypes) => {
  const Community = sequelize.define(
    'Community',
    {
      communityId: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
      },
      title: {
        type: DataTypes.TEXT,
        allowNull: true,
      },
      des: {
        type: DataTypes.TEXT,
        allowNull: true,
      },
      tag: {
        type: DataTypes.TEXT,
        allowNull: true,
      },
      images: {
        type: DataTypes.JSON,
        allowNull: true,
        defaultValue: [],
        get() {
          const rawValue = this.getDataValue('images');
          return rawValue ? JSON.parse(rawValue) : [];
        },
        set(value) {
          this.setDataValue('images', JSON.stringify(value));
        }
      },
      userId: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
          model: 'Users',
          key: 'userId',
        },
        onDelete: 'CASCADE',
        onUpdate: 'CASCADE',
      },
    },
    {
      paranoid: true,
      charset: 'utf8mb4',
      collate: 'utf8mb4_unicode_ci',
    }
  );

  Community.associate = models => {
    Community.belongsTo(models.Users, { foreignKey: 'userId', as: 'creator' });
    Community.belongsToMany(models.Users, { through: models.Wish, foreignKey: 'communityId', as: 'wishingUsers' });
    Community.hasMany(models.Wish, { foreignKey: 'communityId' });
    Community.hasMany(models.Comments, {
      foreignKey: 'communityId',
      as: 'comments'
    });
  };

  return Community;
};
