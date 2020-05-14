# kallisto task for RNA-seq

This task provides a convenience wrapper around [kallisto](https://pachterlab.github.io/kallisto/). It computes gene quantifications from reads in FASTQ format.

## Running

We encourage running this task as a Docker image, which is publicly available through GitHub packages. To pull the image, first [install Docker](https://docs.docker.com/engine/install/), then run
```
docker pull docker.pkg.github.com/krews-community/rnaseq-kallisto-task/rnaseq-kallisto:latest
```
The task requires a pre-built kallisto index to be provided. A kallisto index can be built from a FASTA with gene or transcript sequences by running
```
kallisto index -i index.idx input.fa
```
Then, to perform quantifications, simply run:
```
docker run \
    --volume /path/to/inputs:/input \
    --volume /path/to/outputs:/output \
    docker.pkg.github.com/krews-community/rnaseq-kallisto-task/rnaseq-kallisto:latest \
    java -jar /app/kallisto.jar --r1 /input/r1.fastq.gz --r2 /input/r2.fastq.gz \
        --index /input/index.idx --output-directory /output
```

This will produce an output directory containing quantifications (`output.abundance.tsv`).

### Parameters
This task supports several parameters:
|name|description|default|
|--|--|--|
|r1|path to single-end or pair 1 reads in FASTQ format|(required)|
|r2|path to pair 2 reads in FASTQ format|(none)|
|output-directory|directory in which to write output|(required)|
|strandedness|set if reads are from one strand; forward, reverse, or unstranded|unstranded|
|output-prefix|prefix to use when naming output files|output|
|fragment-length|fragment length, required for single-end reads|(none)|
|sd-fragment-length|SD of fragment length, required for single-end reads|(none)|
|cores|number of cores available to the task|1|
|ram-gb|gigabytes of RAM available to the task|16|

## For developers

The task provides integrated unit testing, which quantifies a small number of reads against a human mitochondrial index and checks that the outputs match expected values. To run the tests, first install Docker and Java, then clone this repo, then run
```
scripts/test.sh
```
Contributions to the code are welcome via pull request.
