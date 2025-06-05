use std::fs::File;
use std::io::{self, BufRead, BufReader, Write};
use std::time::Instant;
use std::env;

/// Merge Sort implementation in Rust.
/// A divide-and-conquer algorithm that divides the input array into two halves, 
/// recursively sorts them, and then merges the sorted halves.
fn merge_sort<T: Ord + Clone>(arr: &mut [T]) {
    let len = arr.len();
    if len <= 1 {
        return;
    }

    let mid = len / 2;
    let mut left = arr[..mid].to_vec();
    let mut right = arr[mid..].to_vec();

    merge_sort(&mut left);
    merge_sort(&mut right);

    merge(arr, &left, &right);
}

/// Merge two sorted slices into a single sorted slice
fn merge<T: Ord + Clone>(arr: &mut [T], left: &[T], right: &[T]) {
    let mut i = 0; // Index for left slice
    let mut j = 0; // Index for right slice
    let mut k = 0; // Index for merged array

    // Merge the two slices
    while i < left.len() && j < right.len() {
        if left[i] <= right[j] {
            arr[k] = left[i].clone();
            i += 1;
        } else {
            arr[k] = right[j].clone();
            j += 1;
        }
        k += 1;
    }

    // Copy remaining elements from left slice if any
    while i < left.len() {
        arr[k] = left[i].clone();
        i += 1;
        k += 1;
    }

    // Copy remaining elements from right slice if any
    while j < right.len() {
        arr[k] = right[j].clone();
        j += 1;
        k += 1;
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
    println!("Rust Merge Sort Performance Test");
    println!("================================");
    
    // Parse command line arguments
    let args: Vec<String> = env::args().collect();
    let filename = args.get(1).map_or("random_list.txt", |s| s.as_str());
    let result_filename = args.get(2).map_or("results_rust.txt", |s| s.as_str());
    
    println!("Using dataset: {}", filename);
    
    // Read data from file
    println!("Reading data from file...");
    let mut data = read_numbers_from_file(filename)?;
    println!("Data size: {} integers", data.len());
    
    // Create a copy for sorting (to preserve original)
    let mut data_copy = data.clone();
    
    // Measure sorting time
    println!("Starting Merge Sort...");
    let start = Instant::now();
    merge_sort(&mut data_copy);
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
    writeln!(result_file, "Rust Merge Sort Results")?;
    writeln!(result_file, "Data size: {}", data.len())?;
    writeln!(result_file, "Execution time: {:.6} seconds", execution_time)?;
    writeln!(result_file, "Elements per second: {:.0}", data.len() as f64 / execution_time)?;
    writeln!(result_file, "Sorted correctly: {}", sorted)?;
    
    println!("Results saved to {}", result_filename);
    
    Ok(())
} 