use std::fs::File;
use std::io::{self, BufRead, BufReader, Write};
use std::time::Instant;
use std::env;

/// Insertion Sort implementation in Rust.
/// Builds the final sorted array one item at a time by comparing each new item with the already sorted portion.
fn insertion_sort<T: Ord + Copy>(arr: &mut [T]) {
    for i in 1..arr.len() {
        let key = arr[i];
        let mut j = i;
        
        while j > 0 && arr[j - 1] > key {
            arr[j] = arr[j - 1];
            j -= 1;
        }
        arr[j] = key;
    }
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
    println!("Rust Insertion Sort Performance Test");
    println!("================================");
    
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
    println!("Starting Insertion Sort...");
    let start = Instant::now();
    insertion_sort(&mut data_copy);
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
    writeln!(result_file, "Rust Insertion Sort Results")?;
    writeln!(result_file, "Data size: {}", data.len())?;
    writeln!(result_file, "Execution time: {:.6} seconds", execution_time)?;
    writeln!(result_file, "Elements per second: {:.0}", data.len() as f64 / execution_time)?;
    writeln!(result_file, "Sorted correctly: {}", sorted)?;
    
    println!("Results saved to {}", result_filename);
    
    Ok(())
} 