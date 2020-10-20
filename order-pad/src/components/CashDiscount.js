import React from 'react';
import {Form} from 'react-bootstrap';


const CashDiscount = (props) => {

    const textAreaStyle = {
        overflow: 'hidden'
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        props.onSubmit();
    }

    const handleChange = (e) => {
        const inputName = e.target.name;
        const inputValue = e.target.value;

        props.onChange(inputName,inputValue);
    }

    return(
        <div className="col-md-8 offset-md-2">
            <br/>

            <h2 className="col-md-6 offset-md-3"> Upload an excel file to calculate CD </h2>
            <br/>

            <Form onSubmit  = {handleSubmit} onChange= {handleChange}>
                <div className="mb-3">
                    <Form.File id="formcheck-api-custom" custom>
                        <Form.File.Input isValid />
                        <Form.File.Label data-browse = "Button text">
                            Upload your excel here
                        </Form.File.Label>
                        <Form.Control.Feedback type="valid">You did it!</Form.Control.Feedback>
                    </Form.File>
                </div>

                <div className="form-group row">
                    <div className="offset-md-4">
                        <button type="submit"  className="btn btn-primary btn-lg"> Submit </button>
                    </div>
                </div>
            </Form>
        </div>
    )
}

export default CashDiscount;