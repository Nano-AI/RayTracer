echo "Compiling..."
g++ ./src/*.cpp -g
echo "Writing..."
./a.out > image.ppm
echo "Displaying..."
feh image.ppm