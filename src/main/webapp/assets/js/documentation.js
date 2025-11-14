/******************************************************************************
 * This piece of work is to enhance authcore project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      documentation.js                                                *
 * Created:   14/11/2025, 01:03                                               *
 * Modified:  14/11/2025, 01:03                                               *
 *                                                                            *
 * Copyright (c)  2025.  Aerosimo Ltd                                         *
 *                                                                            *
 * Permission is hereby granted, free of charge, to any person obtaining a    *
 * copy of this software and associated documentation files (the "Software"), *
 * to deal in the Software without restriction, including without limitation  *
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,   *
 * and/or sell copies of the Software, and to permit persons to whom the      *
 * Software is furnished to do so, subject to the following conditions:       *
 *                                                                            *
 * The above copyright notice and this permission notice shall be included    *
 * in all copies or substantial portions of the Software.                     *
 *                                                                            *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,            *
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES            *
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                   *
 * NONINFINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT                 *
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,               *
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING               *
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE                 *
 * OR OTHER DEALINGS IN THE SOFTWARE.                                         *
 *                                                                            *
 ******************************************************************************/

    let authKey = null;

    async function tryEndpoint(endpoint, payloadId, responseId){
      const payloadText = document.getElementById(payloadId).value;
      const respBox = document.getElementById(responseId);
      try{
        const response = await fetch(endpoint, {
          method:'POST',
          headers:{
            'Content-Type':'application/json',
            ...(authKey ? {'Authorization':'Bearer '+authKey} : {})
          },
          body: payloadText
        });
        const data = await response.json();
        respBox.textContent = JSON.stringify(data,null,2);
        if(endpoint.endsWith('/login') && data.status==='success') authKey = data.message;
      } catch(err){
        respBox.textContent = "⚠️ Error: "+err.message;
      }
    }