use std::fs::File;
use std::io::{self, BufRead, BufReader, Write};
use std::time::Instant;
use std::env;

/// Quick Sort implementation in Rust.
/// A divide-and-conquer algorithm that picks an element as pivot and partitions
/// the array around the pivot.
fn quick_sort<T: Ord>(arr: &mut [T]) {
    if arr.len() <= 1 {
        return;
    }
    
    let pivot_index = partition(arr);
    let (left, right) = arr.split_at_mut(pivot_index);
    quick_sort(left);
    quick_sort(&mut right[1..]);
}

/// Lomuto partition scheme
fn partition<T: Ord>(arr: &mut [T]) -> usize {
    let pivot_index = arr.len() - 1;
    let mut i = 0;
    
    for j in 0..pivot_index {
        if arr[j] <= arr[pivot_index] {
            arr.swap(i, j);
            i += 1;
        }
    }
    
    arr.swap(i, pivot_index);
    i
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
    println!("Rust Quick Sort Performance Test");
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
    println!("Starting Quick Sort...");
    let start = Instant::now();
    quick_sort(&mut data_copy);
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
    writeln!(result_file, "Rust Quick Sort Results")?;
    writeln!(result_file, "Data size: {}", data.len())?;
    writeln!(result_file, "Execution time: {:.6} seconds", execution_time)?;
    writeln!(result_file, "Elements per second: {:.0}", data.len() as f64 / execution_time)?;
    writeln!(result_file, "Sorted correctly: {}", sorted)?;
    
    println!("Results saved to {}", result_filename);
    
    Ok(())
} 