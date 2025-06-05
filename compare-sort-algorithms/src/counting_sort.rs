use std::fs::File;
use std::io::{self, BufRead, BufReader, Write};
use std::time::Instant;
use std::env;

/// Counting Sort implementation in Rust.
/// A non-comparison based sorting algorithm that works by counting the number of objects
/// that have each distinct key value, and using that information to determine their positions.
/// Efficient for small ranges of integers.
fn counting_sort(arr: &mut [i32]) {
    if arr.is_empty() {
        return;
    }

    // Find the maximum and minimum values in the array
    let max = *arr.iter().max().unwrap();
    let min = *arr.iter().min().unwrap();
    let range = (max - min + 1) as usize;

    // Create count array and initialize with zeros
    let mut count = vec![0; range];
    let mut output = vec![0; arr.len()];

    // Store the count of each element
    for &x in arr.iter() {
        count[(x - min) as usize] += 1;
    }

    // Store the cumulative count
    for i in 1..range {
        count[i] += count[i - 1];
    }

    // Build the output array
    for &x in arr.iter().rev() {
        let index = (x - min) as usize;
        output[count[index] - 1] = x;
        count[index] -= 1;
    }

    // Copy the output array to the input array
    arr.copy_from_slice(&output);
}

/// Reads numbers from file and returns as a vector.
fn read_numbers_from_file(filename: &str) -> io::Result<Vec<i32>> {
    let file = File::open(filename)?;
    let reader = BufReader::new(file);
    
    let numbers: Result<Vec<i32>, _> = reader
        .lines()
        .map(|line| line?.trim().parse::<i32>().map_err(|e| io::Error::new(io::ErrorKind::InvalidData, e)))
        .collect();
    
    numbers
}

/// Verifies if the array is sorted.
fn is_sorted<T: Ord>(arr: &[T]) -> bool {
    arr.windows(2).all(|window| window[0] <= window[1])
}

fn main() -> io::Result<()> {
    println!("Rust Counting Sort Performance Test");
    println!("==================================");
    
    // Parse command line arguments
    let args: Vec<String> = env::args().collect();
    let filename = args.get(1).map_or("random_list.txt", |s| s.as_str());
    let result_filename = args.get(2).map_or("results_rust.txt", |s| s.as_str());
    
    println!("Using dataset: {}", filename);
    
    // Read data from file
    println!("Reading data from file...");
    let data = read_numbers_from_file(filename)?;
    println!("Data size: {} integers", data.len());
    
    // Create a copy for sorting (to preserve original)
    let mut data_copy = data.clone();
    
    // Measure sorting time
    println!("Starting Counting Sort...");
    let start = Instant::now();
    counting_sort(&mut data_copy);
    let duration = start.elapsed();
    
    let execution_time = duration.as_secs_f64();
    
    // Verify the array is sorted
    let sorted = is_sorted(&data_copy);
    
    // Results
    println!("Sorting completed: {}", if sorted { "SUCCESS" } else { "FAILED" });
    println!("Execution time: {:.6} seconds", execution_time);
    println!("Elements per second: {:.0}", data.len() as f64 / execution_time);
    
    // Save results to file
    let mut result_file = File::create(result_filename)?;
    writeln!(result_file, "Rust Counting Sort Results")?;
    writeln!(result_file, "Data size: {}", data.len())?;
    writeln!(result_file, "Execution time: {:.6} seconds", execution_time)?;
    writeln!(result_file, "Elements per second: {:.0}", data.len() as f64 / execution_time)?;
    writeln!(result_file, "Sorted correctly: {}", sorted)?;
    
    println!("Results saved to {}", result_filename);
    
    Ok(())
} 