/**
 * 
 */
 new Vue({
            el: "#vue-diagnosis",
            data: {
				body_locations: [],
				symptoms: [],
				diagnosis: [],
                form_data: {
					gender: "0",
					age: "25",
					body_locations: [],
					symptoms: [],
					diagnosis: []
				},
				search: '',
                form_errors: {},
                debounce: null,
                loading: false
            },
            mounted() {
                this.getBodyLocations();
                this.getSymptoms();
            },
            methods: {
                getBodyLocations: function(){
                    this.loading = true;
                    axios
                        .get(`${baseURL}api/body-locations`)
                        .then(({
                            data
                        }) => {
                            console.log(data);
                            this.body_locations = data;
                        })
                        .catch(({
                            response: error
                        }) => {
                            this.handleErrors(error);
                        })
                        .then(() => {
                            this.loading = false;
                       });
                },
                getSymptoms: function(){
                    this.loading = true;
                    axios
                        .get(`${baseURL}api/symptoms`)
                        .then(({
                            data
                        }) => {
                            console.log(data);
                            this.symptoms = data;
                        })
                        .catch(({
                            response: error
                        }) => {
                            this.handleErrors(error);
                        })
                        .then(() => {
                            this.loading = false;
                       });
                },
                specializationReduce: function(specializations){
					return specializations.reduce(function(accl, curVal) {
						if(accl == '') return curVal.Name; 
						return accl + ', ' + curVal.Name;
					}, '');
				},
                validateData: function(){
					if(typeof(this.form_data.first_name) == 'undefined' || this.form_data.first_name == '') {
						return false;
					}
					if(typeof(this.form_data.last_name) == 'undefined' || this.form_data.last_name == '') {
						return false;
					}
					return true;
				},
				resetDiagnosis: function(){
					this.diagnosis = [];
				},
				filterSearch: function(text){
                    return text.toLowerCase().includes(this.search.toLowerCase());
                },
                getDiagnosis: function(){
					this.form_loading = true;
					axios
                        .post(`${baseURL}api/diagnosis`, this.form_data)
                        .then(({data}) => {
                            console.log(data);
                            this.diagnosis = data;
                            if(this.diagnosis.length == 0){
								alert("No diagnosis reached. Select other symptoms");
							}
                        })
                        .catch(({
                            response: error
                        }) => {
                            this.handleErrors(error);
                        })
                        .then(() => {
                            this.form_loading = false;
                       });
				},
                saveSettings: function(){
                    this.form_loading = true;
                    this.form_errors = {};

                    let data = new FormData();
                    for(let k in this.settings){
                        if(k == "organization_logo" && typeof this.settings[k] != 'object') continue;
                        data.append(k, this.settings[k]);
                    }

                    axios
                        .post('/admin/settings', data, {
                            headers: {
                                'Content-Type': 'multipart/form-data'
                            }
                        })
                        .then(() => {
                            successNotify('success');
                        })
                        .catch(({
                            response: error
                        }) => {
                            this.handleErrors(error);
                        })
                        .then(() => {
                            this.form_loading = false;
                       });
                },
                
                handleErrors: function (error) {
                    if (error.status == 422 || error.status == 429) {
                        for (let i in error.data.errors) {
                            this.form_errors[i] = error.data.errors[i][0];
                        }
                        this.$forceUpdate();
                    } else if (error.status == 400) {
                        errorNotify(error.data.error);
                    }
                },
            },
        });