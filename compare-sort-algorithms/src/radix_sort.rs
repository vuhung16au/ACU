use std::fs::File;
use std::io::{self, BufRead, BufReader, Write};
use std::time::Instant;
use std::env;

/// Radix Sort implementation in Rust.
/// A non-comparative sorting algorithm that sorts integers by processing individual digits.
/// It uses counting sort as a subroutine to sort based on each digit position.
fn radix_sort(arr: &mut [i32]) {
    if arr.is_empty() {
        return;
    }

    // Find the maximum number to know number of digits
    let max = *arr.iter().max().unwrap();
    
    // Do counting sort for every digit
    let mut exp = 1;
    while max / exp > 0 {
        counting_sort_for_radix(arr, exp);
        exp *= 10;
    }
}

/// Counting sort for each digit position
fn counting_sort_for_radix(arr: &mut [i32], exp: i32) {
    let n = arr.len();
    let mut output = vec![0; n];
    let mut count = [0; 10]; // count array for digits 0-9
    
    // Store count of occurrences in count[]
    for &x in arr.iter() {
        count[((x / exp) % 10) as usize] += 1;
    }
    
    // Change count[i] so that count[i] contains actual
    // position of this digit in output[]
    for i in 1..10 {
        count[i] += count[i - 1];
    }
    
    // Build the output array
    for &x in arr.iter().rev() {
        let digit = ((x / exp) % 10) as usize;
        output[count[digit] - 1] = x;
        count[digit] -= 1;
    }
    
    // Copy output array to arr[]
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
    println!("Rust Radix Sort Performance Test");
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
    println!("Starting Radix Sort...");
    let start = Instant::now();
    radix_sort(&mut data_copy);
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
    writeln!(result_file, "Rust Radix Sort Results")?;
    writeln!(result_file, "Data size: {}", data.len())?;
    writeln!(result_file, "Execution time: {:.6} seconds", execution_time)?;
    writeln!(result_file, "Elements per second: {:.0}", data.len() as f64 / execution_time)?;
    writeln!(result_file, "Sorted correctly: {}", sorted)?;
    
    println!("Results saved to {}", result_filename);
    
    Ok(())
} 