const {MongoClient} = require('mongodb');
const uri = 'mongodb://root:password@localhost:27017/test?authSource=admin';
const client = new MongoClient(uri);
// const client = new MongoClient(uri, { useUnifiedTopology: true });

async function connectMongo() {
  try {
    await client.connect();
    console.log('Connected to MongoDB');
    return client.db('test'); // 데이터베이스 선택
  } catch (error) {
    console.error('Error connecting to MongoDB:', error);
    throw error;
  }
}

async function closeMongo() {
  try {
    await client.close();
    console.log('MongoDB connection closed');
  } catch (error) {
    console.error('Error closing MongoDB connection:', error);
  }
}

module.exports = {
  connectMongo,
  closeMongo
};
