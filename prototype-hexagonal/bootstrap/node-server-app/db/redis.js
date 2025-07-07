// initRedis.js
const redis = require('redis');
const client = redis.createClient({
  url: 'redis://localhost:6379'
});

async function connectRedis() {
  try {
    await client.connect();
    console.log('Connected to Redis');
    return client; // Redis 클라이언트 반환
  } catch (error) {
    console.error('Error connecting to Redis:', error);
    throw error;
  }
}

async function closeRedis() {
  try {
    await client.quit();
    console.log('Redis connection closed');
  } catch (error) {
    console.error('Error closing Redis connection:', error);
  }
}

module.exports = {
  connectRedis,
  closeRedis
};
