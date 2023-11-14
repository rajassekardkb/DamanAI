package com.example.damanhacker.utlities

class NumberGap {

    fun predictNextNumber(numbers: List<Int>): Int {
        if (numbers.size < 2) {
            // Not enough data to make a prediction
            return -1 // You may want to handle this case differently based on your requirements
        }

        // Get the differences between consecutive numbers
        val differences = mutableListOf<Int>()
        for (i in 1 until numbers.size) {
            differences.add(numbers[i] - numbers[i - 1])
        }

        // Calculate the average difference
        val averageDifference = differences.average().toInt()

        // Predict the next number based on the average difference
        val predictedNumber = numbers.last() + averageDifference

        return predictedNumber
    }

    fun main(list: List<Int>) {
        // Example usage

        val predictedNumber = predictNextNumber(list)
        println("Predicted Next Number: $predictedNumber")
    }

}
