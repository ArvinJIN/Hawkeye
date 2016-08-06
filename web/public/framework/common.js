Array.prototype.removeItem = function(index) {
	if(isNaN(index) || index > this.length) {
		return false;
	}

    for (var i = 0; i < this.length; i++) {
        if (i > index) {
            this[i-1] = this[i];
        }
    }
    this.length -= 1;
};
