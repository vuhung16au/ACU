#!/usr/bin/env python3
"""
Setup script for building Cython extension.
"""

from setuptools import setup, Extension
from Cython.Build import cythonize
import numpy

# Define the extension
extensions = [
    Extension(
        "radix_sort_cy",
        ["radix_sort_cy.pyx"],
        include_dirs=[numpy.get_include()],
        extra_compile_args=["-O3", "-ffast-math"],
        extra_link_args=["-O3"]
    )
]

setup(
    name="radix_sort_cython",
    ext_modules=cythonize(extensions, compiler_directives={
        'language_level': 3,
        'boundscheck': False,
        'wraparound': False,
        'cdivision': True,
        'nonecheck': False
    }),
    zip_safe=False,
)
