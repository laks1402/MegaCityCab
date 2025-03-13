import React, { useState } from "react";
import Navbar from "./navbar";
import { Star } from "lucide-react";
import { motion } from "framer-motion"; 

const GuestReviewsPage = () => {
  const [reviews, setReviews] = useState([
    { name: "John Doe", rating: 5, comment: "Amazing experience! Highly recommend." },
    { name: "Jane Smith", rating: 4, comment: "Great service but the room could be cleaner." },
  ]);
  const [newReview, setNewReview] = useState({ name: "", rating: 0, comment: "" });
  const [averageRating, setAverageRating] = useState(
    reviews.reduce((acc, review) => acc + review.rating, 0) / reviews.length
  );

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewReview((prev) => ({ ...prev, [name]: value }));
  };

  const handleRatingChange = (rating) => {
    setNewReview((prev) => ({ ...prev, rating }));
  };

  const handleAddReview = () => {
    if (newReview.name && newReview.rating && newReview.comment) {
      const updatedReviews = [...reviews, newReview];
      setReviews(updatedReviews);
      setAverageRating(
        updatedReviews.reduce((acc, review) => acc + review.rating, 0) / updatedReviews.length
      );
      setNewReview({ name: "", rating: 0, comment: "" });
    } else {
      alert("Please fill in all fields before submitting!");
    }
  };

  return (
    <>
      <Header />

      <div className="min-h-screen bg-gradient-to-b from-gray-100 to-white py-10">
        <div className="max-w-4xl mx-auto bg-white shadow-xl rounded-2xl p-8">
          <motion.h1
            className="text-3xl font-bold text-gray-800 text-center mb-10"
            initial={{ opacity: 0, y: -50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.6 }}
          >
            Guest Reviews and Ratings
          </motion.h1>

          <motion.div
            className="text-center mb-8"
            initial={{ scale: 0.8, opacity: 0 }}
            animate={{ scale: 1, opacity: 1 }}
            transition={{ duration: 0.6 }}
          >
            <h2 className="text-lg font-semibold text-gray-700">Average Rating</h2>
            <div className="flex justify-center items-center mt-2">
              <div className="text-3xl font-bold text-customdarkBlue">{averageRating.toFixed(1)}</div>
              <div className="flex ml-2">
        
                {Array.from({ length: Math.round(averageRating) }, (_, index) => (
                  <motion.div
                    key={index}
                    animate={{
                      scale: [1, 1.2, 1],
                      rotate: [0, 10, -10, 0],
                    }}
                    transition={{
                      duration: 1.5,
                      repeat: Infinity,
                      delay: index * 0.2, 
                    }}
                  >
                    <Star className="text-custombrown w-6 h-6" />
                  </motion.div>
                ))}
              </div>
            </div>
            <p className="text-gray-500">{reviews.length} reviews</p>
          </motion.div>

          
          <motion.div
            className="mb-8"
            initial={{ opacity: 0, x: -100 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ duration: 0.6 }}
          >
            <h2 className="text-lg font-semibold text-gray-700 mb-4">Leave a Review</h2>
            <div className="space-y-4">
              <input
                type="text"
                name="name"
                value={newReview.name}
                onChange={handleInputChange}
                placeholder="Your name"
                className="w-full border border-gray-300 rounded-lg py-2 px-4 focus:outline-none focus:ring-2 focus:ring-customdarkBlue"
              />
              <textarea
                name="comment"
                value={newReview.comment}
                onChange={handleInputChange}
                placeholder="Your review"
                className="w-full border border-gray-300 rounded-lg py-2 px-4 focus:outline-none focus:ring-2 focus:ring-customdarkBlue"
                rows="4"
              ></textarea>
              <div className="flex items-center space-x-2">
               
                {[1, 2, 3, 4, 5].map((star) => (
                  <motion.div
                    key={star}
                    whileHover={{ scale: 1.2, rotate: 140}}
                    transition={{ type: "spring", stiffness: 200 }}
                    onClick={() => handleRatingChange(star)}
                  >
                    <Star
                      className={`w-6 h-6 cursor-pointer ${
                        newReview.rating >= star ? "text-custombrown" : "text-gray-300"
                      }`}
                    />
                  </motion.div>
                ))}
              </div>
              <motion.button
                onClick={handleAddReview}
                className="bg-custombrown text-white font-bold py-2 px-4 rounded-lg hover:bg-customdarkBlue transition duration-300"
                whileHover={{ scale: 1.05 }}
              >
                Submit Review
              </motion.button>
            </div>
          </motion.div>

       
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.6 }}
          >
            <h2 className="text-lg font-semibold text-gray-700 mb-4">Guest Reviews</h2>
            <div className="space-y-6">
              {reviews.map((review, index) => (
                <motion.div
                  key={index}
                  className="bg-gray-100 rounded-lg p-4 shadow"
                  initial={{ opacity: 0, scale: 0.9 }}
                  animate={{ opacity: 1, scale: 1 }}
                  transition={{ duration: 0.4, delay: index * 0.1 }}
                >
                  <div className="flex justify-between items-center mb-2">
                    <h3 className="font-bold text-gray-800">{review.name}</h3>
                    <div className="flex">
                      {Array.from({ length: review.rating }, (_, idx) => (
                        <motion.div
                          key={idx}
                          animate={{
                            scale: [1, 1.1, 1],
                            rotate: [0, 15, -15, 0],
                          }}
                          transition={{
                            duration: 1.5,
                            repeat: Infinity,
                            delay: idx * 0.2,
                          }}
                        >
                          <Star className="text-custombrown w-5 h-5" />
                        </motion.div>
                      ))}
                    </div>
                  </div>
                  <p className="text-gray-600">{review.comment}</p>
                </motion.div>
              ))}
            </div>
          </motion.div>
        </div>
      </div>

     
      <Footer />
    </>
  );
};

export default GuestReviewsPage;